const selectBt = document.querySelector('.selectBt');
const postImg = document.querySelector('.postImg');

selectBt.addEventListener('click',()=>{
	postImg.click();
});

postImg.onchange = function () {
  var Phamplet = postImg.files[0];

  ext = $(this).val().split('.').pop().toLowerCase(); //확장자

  if($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
    window.alert('이미지 파일이 아닙니다!');

  } else {

    // 읽기
    var reader = new FileReader();
    reader.readAsDataURL(Phamplet);
    //로드 한 후
    reader.onload = function() {

      var tempImage = new Image();
      tempImage.src = reader.result;
      document.querySelector('.preview img').src = tempImage.src;
    }
  }
};