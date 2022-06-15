var swiper = new Swiper('.swiper-container', {
    slidesPerView: 5, // 보여지는 슬라이드 수
    spaceBetween: 20, // 슬라이드 간의 거리(px 단위)
    loop: true, // 슬라이드 무한 반복
    autoplay: {
      delay: 2000,
      disableOnInteraction: false,
    },
    navigation: {
      prevEl: '.swiper-button-prev',
      nextEl: '.swiper-button-next',
    },
});