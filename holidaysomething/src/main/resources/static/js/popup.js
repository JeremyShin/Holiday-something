function OnloadImg(url) {

  var img = new Image();

  img.src = url;

  var img_width = img.width;

  var win_width = img.width + 25;

  var height = img.height + 30;

  var OpenWindow = window.open('', '_blank',
      'width=' + img_width + ', height=' + height
      + ', menubars=no, scrollbars=auto');

  OpenWindow.document.write(
      "<style>body{margin:0px;}</style><img src='" + url + "' width='"
      + win_width + "'>");

}

// 이미지 팝업창으로 원본 보기!!!!
function doImgPop(img) {
  img1 = new Image();
  img1.src = (img);
  imgControll(img);
}

function imgControll(img) {
  if ((img1.width != 0) && (img1.height != 0)) {
    viewImage(img);
  }
  else {
    controller = "imgControll('" + img + "')";
    intervalID = setTimeout(controller, 20);
  }
}

function viewImage(img) {
  W = img1.width;
  H = img1.height;
  O = "width=" + W + ",height=" + H + ",scrollbars=yes";
  imgWin = window.open("", "", O);
  imgWin.document.write("<html><head><title>이미지상세보기</title></head>");
  imgWin.document.write("<body topmargin=0 leftmargin=0>");
  imgWin.document.write("<img src=" + img
      + " onclick='self.close()' style='cursor:pointer;' title ='클릭하시면 창이 닫힙니다.'>");
  imgWin.document.close();
}