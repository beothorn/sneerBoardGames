if(typeof Remote === "undefined"){
    Remote = {
        play : function(p){
            console.log(p);
        }
    };
}

var canvas = document.getElementById("myCanvas");
canvas.height = window.innerHeight;
canvas.width = window.innerWidth;

function myFunc(e) {
    var X = e.touches[0].clientX;
    var Y = e.touches[0].clientY;
    context.drawImage(imageObj, X-(imageObj.width/2), Y-(imageObj.height/2));
    var play = {line:X,column:Y};
    Remote.play(JSON.stringify(play));
    e.preventDefault();
}

canvas.addEventListener("touchstart", myFunc, false);
canvas.addEventListener("mousedown", myFunc, false);

var context = canvas.getContext("2d");

var imageObj = new Image();
imageObj.src = 'piece.png';


function play(play){
    var c = document.getElementById("myCanvas");
    context.drawImage(imageObj, play.line, play.column);
}