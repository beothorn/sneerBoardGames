
function Play(line,column){
    this.line = line;
    this.column = column;
}

if(typeof Remote === "undefined"){
    Remote = {
        play : function(p){
            console.log(p);
            var remotePlay = JSON.parse(p);
            play(new Play(remotePlay.line + 50, remotePlay.column + 50));
        }
    };
}

function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

var canvas = document.getElementById("myCanvas");
canvas.height = window.innerHeight;
canvas.width = window.innerWidth;

function doPlay(coords){
    var x = coords.x;
    var y = coords.y;
    var myPlay = new Play(x-Math.round(Go.size/2) ,y-Math.round(Go.size/2));
    Remote.play(JSON.stringify(myPlay));
    play(myPlay);
}

function touch(evt) {
    var x = evt.touches[0].clientX;
    var y = evt.touches[0].clientY;
    doPlay({x:x,y:y})
    evt.preventDefault();
}

function mouse(evt) {
    var pos = getMousePos(canvas, evt)
    doPlay(pos);
    evt.preventDefault();
}

canvas.addEventListener("touchstart", touch, false);
canvas.addEventListener("mousedown", mouse, false);

var context = canvas.getContext("2d");

var imageObj = new Image();
imageObj.src = "img/xxhdpi/pieces.png";

var board = new Image();
board.src = Go.img;
board.onload = function(){
    console.log(Go.board.length);
    for(var l=0; l<Go.board.length; l++){
        for(var c=0; c<Go.board[l].length; c++){
            context.drawImage(board,
                Go.board[l][c] * Go.size, 0, Go.size, Go.size,
                c * Go.size, l * Go.size, Go.size, Go.size
            );
        }
    }
}



function play(play){
    context.drawImage(imageObj, 0, 0, Go.size, Go.size, play.line, play.column, Go.size, Go.size);
}