document.addEventListener("DOMContentLoaded", function () {
  $("#ac").html(`${sessionStorage.getItem("name")} CABINET`);
  GetData();
  document.getElementById("reg").addEventListener("submit", function () {
    check_registration();
  });
  document.getElementById("username").addEventListener("input", function () {
    check_vvod("username");
  });
  document.getElementById("password").addEventListener("input", function () {
    check_vvod("password");
  });
  document.getElementById("email").addEventListener("input", function () {
    check_simvols();
  });
});

// Создание пользователя
function check_err(p_id, error_text) {
  let log_err = document.getElementById(p_id);
  log_err.innerHTML = error_text;
}

function check_vvod(id) {
  let input = document.getElementById(id);
  let proverka = new RegExp("^([a-z,A-Z,0-9])$");
  let res = "";
  for (elem of input.value) {
    if (proverka.test(elem) && res.length <= 40) {
      res += elem;
    }
  }
  if (res == "") {
    input.value = "";
  } else {
    input.value = res;
  }
}

function check_simvols() {
  let input = document.getElementById("email");
  let res = "";
  for (elem of input.value) {
    if (res.length <= 40) {
      res += elem;
    }
  }
  if (res == "") {
    input.value = "";
  } else {
    input.value = res;
  }
}

function check_registration() {
  document.getElementById("username-error").innerHTML = "";
  document.getElementById("email-error").innerHTML = "";
  document.getElementById("password-error").innerHTML = "";
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;
  let email = document.getElementById("email").value;
  let registration_data = {
    username: username,
    password: password,
    email: email,
  };
  console.log(JSON.stringify(registration_data));
  const url = "http://localhost:8081/api/auth/signup";

  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(registration_data),
  }).then(async (response) => {
    if (response.ok) {
      console.log("успех");
      var dropdowns = document.getElementsByClassName("dropdown-content");
      console.log(dropdowns);
      var i;
      for (i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains("show")) {
          openDropdown.classList.remove("show");
        }
      }
      $(".tb").empty();
      GetData();
      $("form")[0].reset();
    } else if (response.status == 400) {
      let data = await response.json();
      let fields = Object.keys(data);
      let errors = Object.values(data);
      for (let i = 0; i < fields.length; i++) {
        check_err(`${fields[i]}-error`, `${errors[i]}`);
      }
    } else if (response.status == 406) {
      let data = await response.json();
      let error = document.getElementById(`${Object.keys(data)[0]}-error`);
      error.innerHTML = Object.values(data)[0];
    }
  });
}

//
function GetData() {
  const lk_url = "http://localhost:8081/data/all";
  fetch(lk_url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    },
  }).then(async (response) => {
    if (response.ok) {
      let data = await response.json();
      console.log(data);
      Table(data);
    }
  });
}

function Table(arr) {
  $.each(arr, function (key, value) {
    $(".tb").append(`<tr id='${key}'></tr>`);
  });
  console.log(arr);
  let k = 0;

  let m = arr.filter(
    (value) => value["username"] == sessionStorage.getItem("name")
  );
  $(`#${k}`).append(`<td>${m[0]["username"]}</td>`);
  $(`#${k}`).append(`<td>${m[0]["email"]}</td>`);
  $(`#${k}`).append(`<td>${m[0]["visits"]}</td>`);
  let str = "";
  $.each(m[0]["roles"], function (key1, value1) {
    str += value1["name"] + ", ";
  });
  str = str.slice(0, str.length - 2);
  $(`#${k}`).append(`<td>${str}</td>`);
  k++;

  $.each(arr, function (key, value) {
    if (value["username"] != sessionStorage.getItem("name")) {
      $(`#${k}`).append(`<td>${value["username"]}</td>`);
      $(`#${k}`).append(`<td>${value["email"]}</td>`);
      $(`#${k}`).append(`<td>${value["visits"]}</td>`);

      let str = "";
      $.each(value["roles"], function (key1, value1) {
        str += value1["name"] + ", ";
      });
      str = str.slice(0, str.length - 2);
      $(`#${k}`).append(`<td>${str}</td>`);
      if (value["username"] != "admin") {
        $(`#${k}`).append(
          `<td class='btn'> <button class="del" onclick="Delete(this)">Удалить пользователя</button> </td>`
        );
        $(`#${k}`).append(
          `<td class='btn'> <div class="dropdown">
        <button onclick="AddRole(this)" class="dropbtn">Добавить роль</button>
        <div id="myDropdown" class="dropdown-content">
        </div>
      </div> </td>`
        );
        $(`#${k}`).append(
          `<td class='btn'> <div class="dropdown">
        <button onclick="DelRole(this)" class="dropbtn">Удалить роль</button>
        <div id="myDropdown" class="dropdown-content">
        </div>
      </div> </td>`
        );
      }
      k++;
    }
  });
}

function Delete(e) {
  let name = $($(e).parent().parent().children()[0]).html();
  let uname = JSON.stringify({ username: name });
  const url = "http://localhost:8081/data/delete";
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    },
    body: uname,
  }).then(async (response) => {
    if (response.ok) {
      $(".tb").empty();
      GetData();
    }
  });
  return false;
}

function AddRole(e) {
  console.log("др");
  $(e).next().get(0).classList.toggle("show");
  let role = $($(e).parent().parent().parent().children()[3]).html();
  var a = role.split(", ");
  var all_r = ["ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"];
  $(e).next().empty();
  $.each(
    all_r.filter((x) => !a.includes(x)),
    function (key, value) {
      $(e).next().append(`<a id='add'>${value}</a>`);
    }
  );
}

// Роли
function DelRole(e) {
  $(e).next().get(0).classList.toggle("show");
  let role = $($(e).parent().parent().parent().children()[3]).html();
  var a = role.split(", ");
  $(e).next().empty();
  $.each(a, function (key, value) {
    $(e).next().append(`<a id='del'>${value}</a>`);
  });
}

function AddUser(e) {
  $(e).next().get(0).classList.toggle("show");
}

window.onclick = function (event) {
  if (
    !event.target.matches(".dropbtn") &&
    !event.target.matches(".l") &&
    !event.target.matches(".new")
  ) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains("show")) {
        openDropdown.classList.remove("show");
      }
    }
  }
};

// Изменение роли
$(document).on("click", "a#add", function (e) {
  let name = $(
    $(e.currentTarget).parent().parent().parent().parent().children()[0]
  ).html();
  console.log(name);
  let role = $(e.currentTarget).html();
  let mes = JSON.stringify({ userrole: role, username: name });
  const url = "http://localhost:8081/data/add_role";
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    },
    body: mes,
  }).then(async (response) => {
    if (response.ok) {
      $(".tb").empty();
      GetData();
    }
  });
});

$(document).on("click", "a#del", function (e) {
  let name = $(
    $(e.currentTarget).parent().parent().parent().parent().children()[0]
  ).html();
  console.log(sessionStorage.getItem("token"));
  let role = $(e.currentTarget).html();
  let mes = JSON.stringify({ userrole: role, username: name });
  const url = "http://localhost:8081/data/del_role";
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    },
    body: mes,
  }).then(async (response) => {
    if (response.ok) {
      $(".tb").empty();
      GetData();
    }
  });
});
