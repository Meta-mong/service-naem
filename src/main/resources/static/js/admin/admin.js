function move_ticket(){
  window.location.href = "./admin_ticket.html";
}

function move_user(){
  window.location.href = "../../../templates/admin/admin_user.html";
}

function move_notice(){
  window.location.href = "./admin_notice.html";
}

const selectBt = document.querySelector('.selectBt');
const postImg = document.querySelector('.postImg');

selectBt.addEventListener('click',()=>{
	postImg.click();
});