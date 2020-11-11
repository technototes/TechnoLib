function setCookieTheme(theme) {
  var d = new Date();
  d.setTime(d.getTime() + (7*24*60*60*1000));
  var expires = "expires="+ d.toUTCString();
  document.cookie = encodeURIComponent("theme=" + theme + ";" + expires + ";path=/");
  alert("theme=" + theme + ";" + expires + ";path=/");
}
function getCookieTheme() {
  //document.cookie = encodeURIComponent("hello=true;");
  alert(decodeURIComponent(document.cookie));
  if(decodeURIComponent(document.cookie).length != 0){
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(";");
    for(var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf("theme=") == 0) {
        return c.substring(6);
      }
    }
  }else{
    setCookieTheme("light");
    return getCookieTheme();
  }
}
function setDocumentTheme(theme){
  document.getElementsByTagName('link')[0].setAttribute('href', theme+'.css'); 
  alert("theme"+theme);
}

function set(theme){
  if(confirm("Are You Sure You Want To Change Theme?\nYou Will Go Back To Root")){
    setCookieTheme(theme);
    location.reload(true);
  }
}
function init(){
  setDocumentTheme(getCookieTheme());
}

function toggleTheme(){
  alert("cook "+document.cookie);
  set((getCookieTheme() == "light") ? "stylesheet" : "light");
}


