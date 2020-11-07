function getData(t, url, action, id, parent_id) //Функция получения данных через Ajax
{
    var param = "action=" + action;
    if (typeof(id) != "undefined" && id != "")
        param += "&id=" + id;
    if (typeof(parent_id) != "undefined" && parent_id != "")
        param += "&parent_id=" + parent_id;

    jQuery.ajax({ // Ajax запрос
        context : this,
        type : "GET",
        url : url,
        data : param,
        success : function(data) { // Успешный ответ сервера
            if (t == 0) { // Если параметр t=0, значит загружен список
                $('.clickable-row').die('click'); // Отвязываем ранее привязанные события click на строки таблицы
                $("#listid").html(data); // Помещаем полученные данные в контейнер div.listid
                $('.clickable-row').live('click', function (){ // Привязываем заново событие click  на строки таблицы
                    var id = $(this).closest('.clickable-row').find('.id').text();
                    getData(1,url,"edit",id); // По клику на строке будет вызване эта же функция detData с параметром t=1 для загрузки формы
                });
            }
            else if (t == 1) // Если параметр t=1, значит загружена форма
            {
                $('input[type="checkbox"]').die('change'); // Отвязываем ранее привязанные события change на чекбоксы
                if (url.substring(url.lastIndexOf("/") + 1) == "OrderContr") { // Если запрос со страницы Заказы, то делаем связанные списки Город с Клиентом
                    $('#city').die('change'); // Отвязываем ранее привязанные события change на список Городов
                    $("#formid").html(data); // Помещаем полученные данные в контейнер div.formid
                    chainedList('city','id_client',false); // Связываем списки
                    $('#city').live('change', function(){ // Привязываем заново событие change на список городов
                        chainedList('city','id_client',true); // При изменении города отфильтруются клиенты
                    });
                } else {
                    $("#formid").html(data); // Если запрос с любой другой страницы, то просто грузим данные в контейнер div.formid
                }
                $('input[type="checkbox"]').each(function(){ // Выставляем чекбоксы по значениям из полученных данных
                   $(this).attr('checked', this.value == 1);
                });
                $('input[type="checkbox"]').live('change', function(){ // Привязываем событие change на чекбокс
                    this.value = this.checked ? 1 : 0;
                });
                if (typeof(id) == "undefined" || id == "") { // Если не определен id на форме, то делаем кнопку Удалить неактивной
                    $('#del_btn').attr('disabled', 'disabled');
                    $('#del_btn').css('cursor','default');
                }
                else {
                    $('#del_btn').removeAttr('disabled');
                    $('#del_btn').css('cursor','pointer');
                }
                
                if (action == "cleardelete" || action == "delete_img_form") // Сообщение при успешном удалении
                    showMsg("Данные удалены успешно","suc");
            }
            
            if (action == "delete")
                getData(1,url,'cleardelete', '', parent_id);
            
            if (action == "delete_img")
                getData(1,url,'delete_img_form', id, parent_id);
            
            if (typeof(parent_id) != "undefined" && parent_id != "") { // Если открыт popup, то задаем размеры iframe
                var iframe = top.document.getElementById("popupWin").childNodes[0];
                iframe.width = 0;
                iframe.height = 0;
                iframe.width = document.body.scrollWidth;
                iframe.height = document.body.scrollHeight;
            }
        },
        error:  function(xhr, textStatus, errorThrown){ // Неуспешный ответ сервера
            switch(xhr.status){
                case 301: // Таймаут сессии
                    if (top.document.getElementById("popupWin")) {
                        top.window.location.href = "EnterPage.jsp";
                    } else
                        window.location.href = "EnterPage.jsp";
                    break;
                case 501:
                    showMsg("Ошибка удаления данных","err");
                    break;
                default:
                    showMsg("Ошибка получения данных","err");
            }
        }
    })
}

function saveData(url, parent_id) { // Функция сохранения данных
    if (typeof(parent_id) == "undefined")
         parent_id = "";
    var param;
    if (url.substring(url.lastIndexOf("/") + 1) == "DetailContr") {
        param = new FormData();
        param.append('id', $('#id').val());
        param.append('name', $('#name').val());
        param.append('description', $('#description').val());
        param.append('created', $('#created').val());
        param.append('image', $('#image')[0].files[0]);
        
        $.ajax({ // Ajax запрос
        type: 'POST',
        url: url,
        data: param,
        cache: false,
        contentType: false,
        processData: false,
        success: function(data) {
            $("#formid").html(data);
            $('#del_btn').attr('disabled', 'disabled');
            $('#del_btn').css('cursor','default');
            getData(0,url,"list","",parent_id);
            showMsg("Данные сохранены успешно","suc");
        },
        error:  function(xhr, textStatus, errorThrown){
              switch(xhr.status){
                  case 301:
                      if (top.document.getElementById("popupWin")) {
                          top.window.location.href = "EnterPage.jsp";
                      } else
                          window.location.href = "EnterPage.jsp";
                      break;
                  case 415:
                      showMsg("Поля не заполнены!","err");
                      break;
                  default:
                      showMsg("Ошибка входных данных","err");
              }
          }
      });
    } else {
        param = $('#form-submit').serialize();
        
        $.ajax({ // Ajax запрос
        type: 'POST',
        url: url,
        data: param,
        success: function(data) {
            $("#formid").html(data);
            $('#del_btn').attr('disabled', 'disabled');
            $('#del_btn').css('cursor','default');
            getData(0,url,"list","",parent_id);
            showMsg("Данные сохранены успешно","suc");
        },
        error:  function(xhr, textStatus, errorThrown){
              switch(xhr.status){
                  case 301:
                      if (top.document.getElementById("popupWin")) {
                          top.window.location.href = "EnterPage.jsp";
                      } else
                          window.location.href = "EnterPage.jsp";
                      break;
                  case 415:
                      showMsg("Поля не заполнены!","err");
                      break;
                  default:
                      showMsg("Ошибка входных данных","err");
              }
          }
      });
    }
}

function deleteData(url, id, parent_id) { // Удаление данных
    if (typeof(parent_id) == "undefined")
         parent_id = "";
    if (confirm("Удалить выбранную запись?"))
        getData(0,url,'delete', id, parent_id);
}

function deleteImage(url, id) { // Удаление изображения
    if (confirm("Удалить изображение?"))
        getData(0,url,'delete_img', id);
}

var myTimeoutId;

function showMsg(msg, status) { // Вывод информационного сообщения
    $('#msg').css("display", "none");
    clearTimeout(myTimeoutId);
    if (status == "suc") { // успешное
        $('#msg').css("border","2px solid green");
        $('#msg').css("color","green");
        $('#msg').css("background","rgba(144, 238, 144, 0.8)");
    }
    else if (status == "err") { // ошибка
        $('#msg').css("border","2px solid #990000");
        $('#msg').css("color","#990000");
        $('#msg').css("background","rgba(255, 99, 99, 0.8)");
    }
    
    $('#msg').html(msg).fadeIn('slow'); // Медленно показать
    
    myTimeoutId = setTimeout(function() { // Скрыть через 10 секунд
        $("#msg").fadeOut('slow');
    }, 10000);
}

function showModalWin(url, id, set_s) { // Показать popup

    var darkLayer = document.createElement('div'); // слой затемнения
    darkLayer.id = 'shadow'; // id чтобы подхватить стиль
    document.body.appendChild(darkLayer); // включаем затемнение
    
    var name = "";
    if (typeof(id) != "undefined" && id != "") {
        name = ' name=' + id;
        url += '?id=' + id;
        id = ' id='+ id;
    } else
        id = "";
    var sizes = "";
    if (set_s) {
        sizes = 'onload="this.width=this.contentWindow.document.body.scrollWidth; this.height=this.contentWindow.document.body.scrollHeight;"';
    }
    
    $('<div><iframe' + id + name +' src="' + url + '" frameborder="0" scrolling="no"' + sizes + '></iframe></div>').attr({'id':'popupWin', 'class':'modalwin'}).appendTo('body'); // добавляем на страницу iframe
    
    $('#popupWin').children('iframe').load(function() { 
        var img = $('#popupWin').children('iframe').contents().find('img');
        if (img.length) {
            img.attr('width','500');
            $(this).attr('width','500');
            $(this).attr('height', img.height());
        }
        $('#popupWin').css('display', 'inline-block'); // делаем popupWin видимым
    });

    darkLayer.onclick = function () {  // при клике на слой затемнения все исчезнет
        $('#popupWin').css('display', 'none'); // делаем окно невидимым
        $('#popupWin').remove();
        darkLayer.parentNode.removeChild(darkLayer); // удаляем затемнение
        return false;
    };
}

function chainedList(main, chain, clear) { // Связанные списки
    var filter = $("option:selected", "#" + main).text(); // Значение задающего списка
    if (clear) { // если стоит параметр сброса значения подчиненного списка, то сбрасываем
        $("#" + chain).val("");
    }
    $("#" + chain + " option").each(function(){
        var val2 = $(this).attr("data-" + main);
        if (val2 == "" || val2 == filter) { // сравниваем значения фильтра и значений связанного списка
            $(this).show(); // показываем элемент связанного списка
        } else {
            $(this).hide(); // скрываем элемент связанного списка
        }
    });
    return false;
}