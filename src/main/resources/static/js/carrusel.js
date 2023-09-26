$(document).ready(function () {
    $(".slick-carousel").slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: false,
        adaptiveHeight: true,
        prevArrow: '<button class="custom-prev-button carousel-control-prev"><span class="carousel-control-prev-icon" aria-hidden="true"></span></button>',
        nextArrow: '<button class="custom-next-button carousel-control-next"><span class="carousel-control-next-icon" aria-hidden="true"></span></button>'
    });
});
