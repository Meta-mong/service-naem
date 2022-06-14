const dm = document.documentElement;
const topButton = document.querySelector('.back-to-top')

const documentHeight = dm.scrollHeight;

window.addEventListener('scroll', function() {
    let scrollToTop = dm.scrollTop;

    if (documentHeight != 0){
        const actionHeight = documentHeight / 4;

        if(scrollToTop > actionHeight) {
            topButton.style.display = 'block';
        }else {
            topButton.style.display = 'none';
        }
    }
})

topButton.addEventListener('click', function (e) {
    e.preventDefault();
    scrollUp();
})

function scrollUp() {
    let upper = setInterval(function () {
        if (dm.scrollTop != 0) {
            console.log(dm.scrollTop);
            window.scrollBy(0, -55);
        }else {
            clearInterval(upper);
        }
    }, 10);
}