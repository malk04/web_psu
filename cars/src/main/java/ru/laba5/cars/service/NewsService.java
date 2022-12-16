package ru.laba5.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.laba5.cars.exceptions.FileFormatException;
import ru.laba5.cars.exceptions.FileSaveException;
import ru.laba5.cars.models.News;
import ru.laba5.cars.models.NewsImage;
import ru.laba5.cars.pojo.DataTime;
import ru.laba5.cars.pojo.MessageResponse;
import ru.laba5.cars.pojo.NewsRequest;
import ru.laba5.cars.pojo.NewsResponse;
import ru.laba5.cars.repository.NewsImageRepository;
import ru.laba5.cars.repository.NewsRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsImageRepository newsImageRepository;

    private final String path = "D:\\3 semestr\\web_psu\\cars_front\\news_images\\";

    public MessageResponse create(NewsRequest newsRequest) throws FileSaveException, FileFormatException {
        DataTime dataTime = new DataTime();
        String now_data = dataTime.getD()+ "." + dataTime.getMo() + "." + dataTime.getY();

        if (newsRequest.getImage() != null) {
            MultipartFile image = newsRequest.getImage();
            if (image.getContentType().equalsIgnoreCase("image/png") ||
                    image.getContentType().equalsIgnoreCase("image/jpeg")) {
                String fileName = image.getOriginalFilename();
                String filePath = path + UUID.randomUUID() + fileName;
                try {
                    image.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new FileSaveException("Ошибка сохранения файла");
                }
                News news = new News(newsRequest.getTitle(), newsRequest.getText(), now_data);
                NewsImage newsImage = new NewsImage(fileName, image.getContentType(), filePath);
                news.setImage(newsImage);
                newsRepository.save(news);
                newsImageRepository.save(newsImage);
                return new MessageResponse("Новость создана");
            }
            throw new FileFormatException("Недопустимый формат файла");
        }
        News news = new News(newsRequest.getTitle(), newsRequest.getText(), now_data);
        newsRepository.save(news);
        return new MessageResponse("Новость создана");
    }

    public MessageResponse edit(NewsRequest newsRequest, Long id) throws FileSaveException, FileFormatException {
        DataTime dataTime = new DataTime();
        String now_data = dataTime.getD()+ "." + dataTime.getMo() + "." + dataTime.getY();

        if (newsRequest.getImage() != null) {
            MultipartFile image = newsRequest.getImage();
            if (image.getContentType().equalsIgnoreCase("image/png") ||
                    image.getContentType().equalsIgnoreCase("image/jpeg")) {
                String fileName = image.getOriginalFilename();
                String filePath = path + UUID.randomUUID() + fileName;
                News news = newsRepository.findById(id).get();
                news.setTitle(newsRequest.getTitle());
                news.setText(newsRequest.getText());
                if (news.getImage() != null) {
                    NewsImage newsImage = newsImageRepository.findById(news.getImage().getId()).get();

                    String deleteFilePath = newsImage.getFilePath();
                    File deleteImage = new File(deleteFilePath);
                    if (!deleteImage.delete()) {
                        throw new FileSaveException("Ошибка удаления файла");
                    }

                    newsImage.setName(fileName);
                    newsImage.setType(image.getContentType());
                    newsImage.setFilePath(filePath);

                    try {
                        image.transferTo(new File(filePath));
                    } catch (IOException e) {
                        throw new FileSaveException("Ошибка сохранения файла");
                    }
                    news.setImage(newsImage);
                    news.setDate(now_data);
                    newsImageRepository.save(newsImage);
                    newsRepository.save(news);
                    return new MessageResponse("Новость изменена");
                }
                try {
                    image.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new FileSaveException("Ошибка сохранения файла");
                }
                NewsImage newsImage = new NewsImage(fileName, image.getContentType(), filePath);
                news.setImage(newsImage);
                news.setDate(now_data);
                newsRepository.save(news);
                newsImageRepository.save(newsImage);
                return new MessageResponse("Новость изменена");
            }
            throw new FileFormatException("Недопустимый формат файла");
        }
        News news = newsRepository.findById(id).get();
        news.setTitle(newsRequest.getTitle());
        news.setText(newsRequest.getText());
        news.setDate(now_data);
        newsRepository.save(news);
        return new MessageResponse("Новость изменена");
    }

    public MessageResponse delete(Long id) throws FileSaveException {
        NewsImage newsImage = newsRepository.findById(id).get().getImage();
        if (newsImage != null) {
            String deleteFilePath = newsImage.getFilePath();
            File deleteFile = new File(deleteFilePath);
            if (!deleteFile.delete()) {
                throw new FileSaveException("Ошибка удаления файла");
            }
        }
        newsRepository.deleteById(id);
        return new MessageResponse("Новость удалена");
    }

    public MessageResponse deleteImage(Long id) {
        News news = newsRepository.findById(id).get();
        if (news.getImage() != null) {
            Long imageId = news.getImage().getId();
            news.setImage(null);
            newsImageRepository.deleteById(imageId);
            newsRepository.save(news);
            return new MessageResponse("Изображение удалено");
        }
        return new MessageResponse("Нет изображения");
    }

    public List<NewsResponse> getAllNews() {
        List<News> allNews = newsRepository.findAll();
        List<NewsResponse> allNewsResponse = new ArrayList<>();
        for (News news : allNews) {
            allNewsResponse.add(new NewsResponse(news.getId(), news.getTitle(), news.getText(),
                    news.getDate(), news.getImage() != null ? news.getImage().getName() : null, news.getImage() != null ? news.getImage().getFilePath() : null));
        }
        return allNewsResponse;
    }
}
