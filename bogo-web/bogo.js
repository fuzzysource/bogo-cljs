var UserTextInput = document.getdomsByTagName("input");
var UserTextArea = document.getdomsByTagName("textarea");
var UserTextEnvironment = UserTextInput.concat(UserTextArea);

var bgBackspace = (function () {
    var ev = new KeyboardEvent("keydown",);
    ev.initCustom

}) ()

var bgCountBackSpaces = function (oldString, newString) {
    var i;
    while (oldString[i] === newString[i]) {
        i++;
    }
    return (oldString.length - i);
}

var bgDiff = function (oldString, newString) {
    var i;
    while (oldString[i] === newString[i]) {
        i++;
    }
    return substring(newString, i)
}

var bgSendBackspaces = function (dom, num) {
    var i;
    for (i = 0; i < num; i++) {
        dom.
    }
}

var initBogo(dom) {
    dom.currentString = "";
    dom.onkeypress = function (ev) {
        var char = String.fromCharCode(ev.charCode);
        var oldString = this.currentString;
        var newString = bgProcessKey(this.oldString, char);
        sendBackspace(bgCountBackSpaces(oldString, newString));
        sendNewChar(this, bgDiff(oldString, newString));
    }
}

UserTextEnvironment.forEach(function(dom) {
    e.addEventListioner("focus", function (ev) {
        initBogo(this);
    });
    e.addEventListioner("blur", function (ev) {
        clearBogo(this);
    });
})
