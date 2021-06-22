
    
//************ 공통 모달 팝업 ************//
var ModalPopup = {
    "open" : function() {
        //모달팝업 바깥쪽 배경색 및 화면고정 페이드인
        $('.modal_popup').fadeIn(600,function(){
            //popup_wrap의 자식요소 열기 (popup)
            $(this).children('.popup_cont').slideDown(200);
        });
        $('body').css('overflow','hidden');

        //팝업 중앙정렬 함수
        PopupCenter();
    },

    "close" : function() {
        //도큐먼트에서 popup를 찾아서 닫기
        $(document).find('.popup_cont').slideUp(500,function(){
            //popup이 속해있는 popup_wrap 페이드아웃
            $(this).parent('.modal_popup').fadeOut(600);
            $('.popup_cont').css('display','none');
        });
        $('body').css('overflow', 'visible');   
    },
}


//팝업 중앙정렬
function PopupCenter() {
    var height = $('.popup_cont').height();
    $('.popup_cont').css({'top':($(window).height() - height) / 2});
};

//브라우저 창 사이즈 조절할때도 중앙정렬
$(window).resize(function() {
    PopupCenter();
});





$(document).ready(function () {



    //header dropdown
    var my_dropdown = $("div.mypage_sc div.mypage_dropdown");    

    $(document).on('click', '.ShowDropdown', function(){
        my_dropdown.slideDown(200);
        $(this).removeClass('ShowDropdown');
        $(this).addClass('CloseDropdown');
    });
    $(document).on('click', '.CloseDropdown', function(){
        my_dropdown.slideUp(200);
        $(this).removeClass('CloseDropdown');
        $(this).addClass('ShowDropdown');
    });



    //header dropdown-hover
    var dropdown_link = $("div.mypage_sc div.mypage_dropdown ul.mypage_menu li a");

    $(dropdown_link).hover(
        function () {
            $(this).addClass('on');
        },
        function () {
            $(this).removeClass('on');
        }
    )



    //채팅박스 열기
    $(document).on('click', 'div.chat_sc .OpenChat', function(){
        $('div.chat_sc div.chat_box').show();
        $('div.chat_sc button.btn_chat').addClass('close CloseChat');
        $('div.chat_sc button.btn_chat').removeClass('open OpenChat');
    });

    //채팅박스 닫기
    $(document).on('click', 'div.chat_sc .CloseChat', function(){
        $('div.chat_sc div.chat_box').hide();
        $('div.chat_sc button.btn_chat').addClass('open OpenChat');
        $('div.chat_sc button.btn_chat').removeClass('close CloseChat');
    });

    //채팅 날짜선택 열기
    $(document).on('click', 'div.chat_box .ShowSave', function(){
        $(this).next('div.chat_box_header ul.save_sc').show();
    });

    //채팅 날짜선택 닫기
    $(document).on('click', 'div.chat_box .CloseSave', function(){
        $(this).parents('div.chat_box_header ul.save_sc').hide();
    });



    // 모달팝업 1 열기
    $('.ModalOpen').click(function(){
         //모달팝업 바깥쪽 배경색 및 화면고정 페이드인
         $('.modal_popup').fadeIn(600,function(){
            //popup_wrap의 자식요소 열기 (popup)
            $(this).children('.popup_cont').slideDown(200);
        });
        $('body').css('overflow','hidden');

        //팝업 중앙정렬 함수
        PopupCenter();
    });

    // 모달팝업 1 닫기
    $('.ModalClose').click(function(){
        //도큐먼트에서 popup를 찾아서 닫기
        $(document).find('.popup_cont').slideUp(500,function(){
            //popup이 속해있는 popup_wrap 페이드아웃
            $(this).parent('.modal_popup').fadeOut(600);
            $('.popup_cont').css('display','none');
        });
        $('body').css('overflow', 'visible');   
    });



    // 모달팝업 2 열기
    $('.ModalOpen_2').click(function(){
        //모달팝업 바깥쪽 배경색 및 화면고정 페이드인
        $(this).next('.modal_popup').fadeIn(600,function(){
            //popup_wrap의 자식요소 열기 (popup)
            $(this).children('.popup_cont').slideDown(200);
        });
        $('body').css('overflow','hidden');

        //팝업 중앙정렬 함수
        PopupCenter();
    });

    // 모달팝업 2 닫기
    $('.ModalClose_2').click(function(){
        //도큐먼트에서 popup를 찾아서 닫기
        $(this).parent().parent('.popup_cont').slideUp(500,function(){
            //popup이 속해있는 popup_wrap 페이드아웃
            $(this).parent('.modal_popup').fadeOut(600);
            $('.popup_cont').css('display','none');
        });
        $('body').css('overflow', 'visible');   
    });



});