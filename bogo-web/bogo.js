var url = "ws://0.0.0.0:8080";
var inputDoms = Array.prototype.slice.call(document.getElementsByTagName("input"));
var textareaDoms = Array.prototype.slice.call(document.getElementsByTagName("textarea"));
var textDoms = inputDoms.concat(textareaDoms);

var connectBogoServer = function (url) {
  var bogo = new WebSocket(url);
  bogo.onopen = function (ev) {
    this.send("CHAO\nf");
  }
  return bogo;
}

document.bogo = connectBogoServer(url);

var bgProcessKey = function (dom, char) {
  var bogo = document.bogo;
  var content = dom.value;
  var oldString = dom.oldString;
  bogo.send(oldString + "\n" + char);
  bogo.onmessage = function (ev) {
    var newString = ev.data;
    var splitIndex = content.length - oldString.length;
    dom.value = content.substr(0, splitIndex) + newString;
    dom.oldString = newString;
  }
}

var isCharacter = function (key) {
  if ((key.length > 1) || (key == " "))
    return false;
  return true;
}

var isBackspace = function (key) {
  return (key === "Backspace")
}

var isSpecialKey = function (ev) {
  return (ev.altKey || ev.ctrlKey || ev.metaKey)
}

var hasSelectedText = function (dom) {
  var selectedText = dom.value.slice(dom.selectionStart, dom.selectionEnd);
  return selectedText;
}

var keypressListener = function (ev) {
    var dom = document.activeElement;
    var oldString = dom.oldString;
    if (hasSelectedText(dom)){
      dom.oldString = "";
      return true;
    }

    if (isSpecialKey (ev))
      return true;
    if (isCharacter (ev.key)){
      ev.preventDefault();
      bgProcessKey(dom, ev.key);
    } else {
      if (isBackspace (ev.key)) {
        dom.oldString = dom.oldString.substr(0, oldString.length - 1)
      }
        dom.oldString = "";
    }
}

var startBogo = function (dom) {
  dom.oldString = "";
  document.addEventListener("keypress", keypressListener);
}

var stopBogo = function (dom) {
  dom.oldString = "";
  document.removeEventListener("keypress",keypressListener);
}

textDoms.forEach(function (dom) {
  dom.onfocus = function(ev) {
    startBogo(this);
  }
  dom.onblur = function(ev) {
    stopBogo(this);
  }
});

