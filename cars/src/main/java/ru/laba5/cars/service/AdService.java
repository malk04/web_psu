package ru.laba5.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.laba5.cars.exceptions.FileFoundException;
import ru.laba5.cars.exceptions.FileSaveException;
import ru.laba5.cars.models.Ad;
import ru.laba5.cars.models.AdFile;
import ru.laba5.cars.models.User;
import ru.laba5.cars.pojo.AdRequest;
import ru.laba5.cars.pojo.AdResponse;
import ru.laba5.cars.pojo.DataTime;
import ru.laba5.cars.pojo.MessageResponse;
import ru.laba5.cars.repository.AdFileRepository;
import ru.laba5.cars.repository.AdRepository;
import ru.laba5.cars.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AdService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdRepository adRepository;

    @Autowired
    AdFileRepository adFileRepository;

    private final String path = "D:\\3 semestr\\web_psu\\cars_front\\ads_files\\";

    public MessageResponse createAd(AdRequest adRequest) throws FileSaveException, FileFoundException {
        if (adRequest.getFile() == null) {
            throw new FileFoundException("Нет файла");
        }
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        MultipartFile file = adRequest.getFile();
        String fileName = file.getOriginalFilename();
        String filePath = path + UUID.randomUUID() + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileSaveException("Ошибка сохранения файла");
        }
        AdFile adFile = new AdFile(fileName, file.getContentType(), filePath);
        User user = userRepository.findById(userDetails.getId()).get();
        DataTime dataTime = new DataTime();
        String now_data = dataTime.getD()+ "." + dataTime.getMo() + "." + dataTime.getY();
        Ad ad = new Ad(adRequest.getTheme(), adRequest.getText(), now_data);
        ad.setFile(adFile);
        ad.setUser(user);
        adRepository.save(ad);
        adFileRepository.save(adFile);
        return new MessageResponse("Заявка создана");
    }

    public MessageResponse deleteAd(Long id) throws FileSaveException {
        String deleteFilePath = adRepository.findById(id).get().getFile().getFilePath();
        File deleteFile = new File(deleteFilePath);
        if (!deleteFile.delete()) {
            throw new FileSaveException("Ошибка удаления файла");
        }
        adRepository.deleteById(id);
        return new MessageResponse("Заявка с id=" + id + " удалена");
    }

    public List<AdResponse> getAllUserAds() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        User user = userRepository.findById(userDetails.getId()).get();
        List<Ad> ads = adRepository.findAllAdsByUser(user);
        List<AdResponse> adsData = new ArrayList<>();
        for (Ad ad : ads) {
            adsData.add(new AdResponse(ad.getId(), ad.getTheme(), ad.getText(), ad.getFile().getName(), ad.getCreate_date()));
        }
        return adsData;
    }

    public MessageResponse editAd(AdRequest adRequest, Long id) throws FileSaveException {
        Ad ad = adRepository.findById(id).get();
        ad.setTheme(adRequest.getTheme());
        ad.setText(adRequest.getText());

        if (adRequest.getFile() == null) {
            adRepository.save(ad);
            return new MessageResponse("Заявка изменена");
        }
        MultipartFile file = adRequest.getFile();

        String fileName = file.getOriginalFilename();
        String filePath = path + UUID.randomUUID() + fileName;

        AdFile adFile = adFileRepository.findById(ad.getFile().getId()).get();
        String deleteFilePath = adFile.getFilePath();
        adFile.setName(fileName);
        adFile.setType(file.getContentType());
        adFile.setFilePath(filePath);

        File deleteFile = new File(deleteFilePath);
        if (!deleteFile.delete()) {
            throw new FileSaveException("Ошибка удаления файла");
        }
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileSaveException("Ошибка сохранения файла");
        }
        adRepository.save(ad);
        ad.setFile(adFile);
        adFileRepository.save(adFile);
        return new MessageResponse("Заявка изменена");
    }
}
