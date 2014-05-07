var url = "ws://0.0.0.0:8080";
var UserTextFields = $("input, textarea");

var connectBogoServer = function (url) {
  var bogo = new WebSocket(url);
  bogo.onopen = function (ev) {
    this.send("CHAO\nf");
  }
  return bogo;
}

var bogo = connectBogoServer(url);

var bgProcessKey = function (dom, char) {
  var content = $(dom).val();
  var oldString = dom.oldString;
  bogo.send(oldString + "\n" + char);
  bogo.onmessage = function (ev) {
    var newString = ev.data;
    var splitIndex = content.length - oldString.length;
    $(dom).val(content.substr(0, splitIndex) + newString);
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
  var selectedText = $(dom).val().slice(dom.selectionStart, dom.selectionEnd);
  return selectedText;
}

var startBogo = function (dom) {
  dom.oldString = "";
  var oldString = dom.oldString;

  $(document).bind("keypress", function (ev) {
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
        dom.oldString = oldString.substr(0, oldString.length - 1)
      }
        dom.oldString = "";
    }
  });
}

var stopBogo = function (dom) {
  dom.oldString = "";
  $(document).unbind("keypress");
}

UserTextFields.focus(function (ev) {
  startBogo(this);
});
UserTextFields.blur(function (ev) {
  stopBogo(this);
});
