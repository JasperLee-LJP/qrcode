'use strict';

// var BASE_URL = 'http://120.79.197.5:8080/qrcode/api';
var BASE_URL = '/api';
var TOKEN_KEY = "jwtToken";

(function ($) {

    "use strict";

    // PRE LOADER

    $(window).load(function () {
        $('.preloader').fadeOut(1000); // set duration in brackets    
    });

    // MENU
    $('.navbar-collapse a').on('click', function () {
        $(".navbar-collapse").collapse('hide');
    });

    $(window).scroll(function () {
        if ($(".navbar").offset().top > 50) {
            $(".navbar-fixed-top").addClass("top-nav-collapse");
        } else {
            $(".navbar-fixed-top").removeClass("top-nav-collapse");
        }
    });

    // PARALLAX EFFECT
    $.stellar({
        horizontalScrolling: false
    });

    // MAGNIFIC POPUP
    $('.image-popup').magnificPopup({
        type: 'image',
        removalDelay: 300,
        mainClass: 'mfp-with-zoom',
        gallery: {
            enabled: true
        },
        zoom: {
            enabled: true, // By default it's false, so don't forget to enable it

            duration: 300, // duration of the effect, in milliseconds
            easing: 'ease-in-out', // CSS transition easing function

            // The "opener" function should return the element from which popup will be zoomed in
            // and to which popup will be scaled down
            // By defailt it looks for an image tag:
            opener: function opener(openerElement) {
                // openerElement is the element on which popup was initialized, in this case its <a> tag
                // you don't need to add "opener" option if this code matches your needs, it's defailt one.
                return openerElement.is('img') ? openerElement : openerElement.find('img');
            }
        }
    });
    // SMOOTH SCROLL
    $(function () {
        $('.custom-navbar .nav_c a').on('click', function (event) {
            var $anchor = $(this);
            $('html, body').stop().animate({
                scrollTop: $($anchor.attr('href')).offset().top - 49
            }, 1000);
            event.preventDefault();
        });
    });
    start();
})(jQuery);

function start() {
    if (getJwtToken()) {
        getUserInfo(function (err, msg) {
            if (err) {
                $('.user').hide();
                $('.un_user').show();
            } else {
                localStorage.username = msg.username;
                $('.user').show();
                $('.un_user').hide();
                $('.user_name').text(localStorage.username);
                menu();
            }
        })
    } else {
        $('.user').hide();
        $('.un_user').show();
    }
    //娉ㄥ唽
    $('#register_btn').on('click', function () {
        register();
    });
    //鐧诲綍
    $('#login_btn').on('click', function () {
        login();
    });
    //娣诲姞
    $("#add_code").on('click', function () {
        add_code();
    });
    //鍒犻櫎
    $("#delete_code").on('click', function () {
        delete_code();
    });
    //淇敼
    $("#fix_code").on('click', function () {
        fix_code();
    });
    //鏌ヨ
    $("#search_code").on('click', function () {
        search_code();
    });
    //娓稿
    $('#visit').on('click', function () {
        visit();
    });
}

function getJwtToken() {
    return localStorage.getItem(TOKEN_KEY);
}

function setJwtToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

function removeJwtToken() {
    localStorage.removeItem(TOKEN_KEY);
}

function createAuthorizationTokenHeader() {
    var token = getJwtToken();
    if (token) {
        return { "Authorization": "Bearer " + token };
    } else {
        return {};
    }
}

function visit() {
    login('1234', '12345678')
}

function search_code() {
    var address = $('#search_address').val().trim();
    var name = $('#search_name').val().trim();
    var url = $('#search_url').val().trim();
    $.ajax({
        url: BASE_URL + '/qrcodes?name=' + name + '&url=' + url + '&address=' + address,
        headers: createAuthorizationTokenHeader(),
        success: add_content,
        error: function (e) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('鏈壘鍒扮鍚堟潯浠剁殑鏉＄洰!');
        }
    });
    // $.ajax({
    //     url: BASE_URL + '/2code_php/search.php',
    //     data: 'u=' + u + '&c=' + url + '&a=' + address + '&n=' + name,
    //     success: function success(msg) {
    //         var data = JSON.parse(msg);
    //         if (data.code == '1') {
    //             add_content(msg);
    //         } else {
    //             $('#modal-form3').modal('show');
    //             $('#modal-form3 .info_text').text('鏈壘鍒扮鍚堟潯浠剁殑鏉＄洰!');
    //         }
    //     }

    // });
}

function fix_code() {
    var id = localStorage.currentId;
    var address = $('#fix_address').val();
    var name = $('#fix_name').val();
    var url = $('#fix_url').val();
    var comment = $('#fix_info').val();
    if (!name || !url) {
        $('#modal-form3').modal('show');
        $('#modal-form3 .info_text').text('璇峰畬鏁磋緭鍏�');

        return;
    }
    $.ajax({
        url: BASE_URL + '/qrcode/' + id,
        method: "PUT",
        headers: createAuthorizationTokenHeader(),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        data: JSON.stringify({
            url: url,
            address: address,
            name: name,
            comment: comment
        }),
        success: function success(msg) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('淇敼鎴愬姛!');

            location.reload();
        },
        error: function (e) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('淇敼澶辫触!');
        }

    });
}

function delete_code() {
    var id = localStorage.currentId;
    $.ajax({
        url: BASE_URL + '/qrcode/' + id,
        type: "DELETE",
        headers: createAuthorizationTokenHeader(),
        success: function success(msg) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('鍒犻櫎鎴愬姛!');

            location.reload();
        },
        error: function (e) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('鍒犻櫎澶辫触!');
        }
    });
}

function add_code() {
    var username = localStorage.username;
    var url = $('#add_url').val().trim();
    var comment = $('#add_info').val().trim();
    var name = $('#add_name').val().trim();
    var address = '';
    if ($('#province').val() != -1) {
        address += $('#province option[value="' + $('#province').val() + '"]').text() + ' ';
    }
    if ($('#city').val() != -1) {
        address += $('#city option[value="' + $('#city').val() + '"]').text() + ' ';
    }
    if ($('#district').val() != -1) {
        address += $('#district option[value="' + $('#district').val() + '"]').text() + ' ';
    }
    if (!url || !name) {
        $('#modal-form3').modal('show');
        $('#modal-form3 .info_text').text('璇峰畬鏁磋緭鍏�!');

        return;
    }
    $.ajax({
        url: BASE_URL + '/qrcode',
        method: "POST",
        contentType: 'application/json;charset=utf-8',
        headers: createAuthorizationTokenHeader(),
        dataType: 'json',
        data: JSON.stringify({
            url: url,
            address: address,
            name: name,
            comment: comment
        }),
        success: function success(msg) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('鏂板鎴愬姛!');
            location.reload();
        },
        error: function (e) {
            $('#modal-form3').modal('show');
            $('#modal-form3 .info_text').text('鏂板澶辫触!');
        }
    });
}

function login(username, password) {
    var u = username || $('#user_g').val();
    var p = password || $('#password_g').val();
    $.ajax({
        type: 'POST',
        url: BASE_URL + '/authenticate',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({ 'username': u, 'password': p }),
        dataType: 'json',
        success: function success(msg) {
            console.log('login msg: ', msg)
            if (msg.id_token) {
                setJwtToken(msg.id_token);
                getUserInfo(function (err, msg) {
                    if (err) {
                        $('#login_info').html('鑾峰彇鐢ㄦ埛淇℃伅閿欒!');
                    } else {
                        localStorage.username = msg.username;
                        $('#modal-form').modal('hide');
                        $('.user').show();
                        $('.un_user').hide();
                        $('.user_name').text(localStorage.username);
                        menu();
                        $('#login_info').html(' ');
                    }
                })
            } else {
                $('#login_info').html('璐﹀彿瀵嗙爜閿欒!');
            }
        },
        error: function (e) {
            $('#login_info').html('璐﹀彿瀵嗙爜閿欒!');
        }
    });
}

function getUserInfo(callback) {
    $.ajax({
        type: 'GET',
        url: BASE_URL + '/user',
        contentType: 'application/json;charset=utf-8',
        headers: createAuthorizationTokenHeader(),
        dataType: 'json',
        success: function success(msg) {
            callback(null, msg)
        },
        error: function (e) {
            callback(e)
        }
    })
}

function register() {
    var u = $('#user_gr').val();
    var p = $('#password_gr').val();
    $.ajax({
        type: 'POST',
        url: BASE_URL + '/register',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({ 'username': u, 'password': p }),
        dataType: 'json',
        success: function success(msg) {
            login(u, p);
            $('#modal-form-register').modal('hide');
            $('#register_info').html(' ');
        },
        error: function (e) {
            $('#register_info').html('鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒!');
        }

    });
}

function add_content(codes) {
    $('.code_info').remove();
    for (var i = 0; i < codes.length; i++) {
        var html = '';
        var code_id = codes[i].id;
        var code_id2 = '';
        if (code_id > 99) {
            code_id2 = code_id;
        } else if (code_id > 9 && code_id <= 99) {
            code_id2 = '0' + code_id;
        } else {
            code_id2 = '00' + code_id;
        }
        html += '<div class="col-md-6 col-sm-6 code_info">\n' +
            '<div class="media blog-thumb">\n' +
            '<div class="media-object media-left ele' + i + '">\n' +
            '</div>\n' +
            '<div class="media-body blog-info">\n' +
            '<small><i class="fa fa-clock-o"></i>\u626B\u7801\u6B21\u6570:' + codes[i].scanCount + '</small>\n' +
            '<h3><a href="javascript:void(0)">' + codes[i].name + '</a></h3>\n' +
            '<p>I D : ' + code_id2 + '</p>\n' +
            '<p>\u7F51\u5740 : ' + codes[i].url + '</p>\n' +
            '<p>\u533A\u57DF : ' + (codes[i].address || '鏃�') + '</p>\n' +
            '<p>\u5907\u6CE8 : ' + (codes[i].comment || '鏃�') + '</p>\n' +
            '<button class="btn section-btn" onclick=\'fix("' + encodeURI(codes[i].id) + '","' + encodeURI(codes[i].address) + '","' + encodeURI(codes[i].name) + '","' + encodeURI(codes[i].url) + '","' + encodeURI(codes[i].comment) + '")\'>\u4FEE\u6539</button>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>';
        $('#code_b').append(html);
        var ele = '.ele' + i;
        var url = location.origin + '/api/qrcode/scan/' + codes[i].id;
        paint(url, ele);
    }
}

function menu() {
    $.ajax({
        url: BASE_URL + '/qrcodes',
        headers: createAuthorizationTokenHeader(),
        success: add_content,
        error: function (e) {
            removeJwtToken();
            start();
        }
    });
}

function fix(id, a, n, c, i) {
    localStorage.currentId = id;
    $('#fix_address').val(decodeURI(a));
    $('#fix_name').val(decodeURI(n));
    $('#fix_url').val(decodeURI(c));
    $('#fix_info').val(decodeURI(i));
    $('#modal-form2').modal('show');
}

function paint(url, ele) {
    outputQRCod(url, 200, 200); //杞崲涓枃瀛楃涓�
    function toUtf8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if (c >= 0x0001 && c <= 0x007F) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | c >> 12 & 0x0F);
                out += String.fromCharCode(0x80 | c >> 6 & 0x3F);
                out += String.fromCharCode(0x80 | c >> 0 & 0x3F);
            } else {
                out += String.fromCharCode(0xC0 | c >> 6 & 0x1F);
                out += String.fromCharCode(0x80 | c >> 0 & 0x3F);
            }
        }
        return out;
    }

    //鐢熸垚浜岀淮鐮�
    function outputQRCod(txt, width, height) {
        //鍏堟竻绌�
        $(ele).empty();
        //涓枃鏍煎紡杞崲
        var str = toUtf8(txt);
        //鐢熸垚浜岀淮鐮�
        $(ele).qrcode({
            render: "canvas", //canvas鍜宼able涓ょ娓叉煋鏂瑰紡
            width: width,
            height: height,
            text: str
        });
    }
}

function exit() {
    localStorage.username = '';
    removeJwtToken();
    location.reload();
}