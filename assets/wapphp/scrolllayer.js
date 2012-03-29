(function($){$.fn.scrollLayer = function(options){var setting = {direction:'X', //方向。X代表水平方向，Y代表垂直方向
wrapEl:'.wrap', //外层容器。用来overflow遮罩的层
touchEl:'.holder', //touch层容器。用来包裹列表内层元素的层
childEl:'li', //内层元素。用来放图的层
margin:5, //间距。以实际元素间距为准
speed:0.9, //加速度。一般取0.9-1之间
prevent:true //阻止超链接默认事件
};if(options){$.extend(setting, options);};var width = 0, height = 0, wrapWidth = 0, wrapHeight = 0;var scroll;var touch = $(this).find(setting.touchEl);return $(this).each(function(){
var child = $(this).find(setting.childEl);//计算内部总高/宽度
if(setting.direction == 'X'){
for(var i=0;i<child.size();i++) {width += child.eq(i).width() + setting.margin*2;}touch.width(width);wrapWidth = $(this).find(setting.wrapEl).width();}else{
for(var i=0;i<child.size();i++) {height += child.eq(i).height() + setting.margin*2;}touch.height(height);wrapHeight = $(this).find(setting.wrapEl).height();}
//屏蔽超链接事件
if(setting.prevent){$(this).find('a').click(function(){return false;});}touch[0].onmousedown = touch[0].ontouchstart = startDrag;
//开始拖放
function startDrag(e){var pos = [touch.position().left, touch.position().top];var startPoint = getClient(e); //开始点
var startTime = new Date().getTime();
var step = 10, //滚动精度
endPoint,speed,distance = 0;if(setting.direction == 'X'){var min = -width + wrapWidth + setting.margin*2; //右侧范围值（负值）
}else{//var min = -width + $(setting.outerEl).height();
var min = -height + wrapHeight + setting.margin*2; //下侧范围值（负值）
}var max = 0;clearInterval(scroll); //清除计时器
touch[0].ontouchmove = touch[0].onmousemove = moveDrag;touch[0].ontouchend = document.onmouseup = endDrag;return false;
//拖放结束后的滚动加速度
function extraScroll() {
distance += Math.round(speed*(step/1000));
newPos = endPoint+distance;touch[0].style.left = newPos + 'px';
if (newPos > max || newPos < min) {
clearInterval(scroll);return;
}
if( Math.abs(newPos) % wrapWidth == 0){
clearInterval(scroll);return;
}
}
function moveDrag(e){var currentPos = getClient(e);if(setting.direction == 'X'){var newPos = (currentPos - startPoint) + pos[0];}else{var newPos = (currentPos - startPoint) + pos[1];}if (newPos <= max && newPos >= min) {if(setting.direction == 'X'){touch[0].style.left = newPos + 'px';}else{//this.style.top = newPos - 11 + 'px';
touch[0].style.top = newPos + 'px';}}}//结束拖放
function endDrag(e){var end = getClient(e);if(setting.direction == 'X'){endPoint = touch[0].offsetLeft;}else{endPoint = touch[0].offsetTop;}var endTime = new Date().getTime();var dist = end - startPoint;var time = endTime - startTime;speed = dist/(time/1000);scroll = setInterval(extraScroll,step);touch[0].ontouchmove = touch[0].ontouchend = touch[0].onmousemove = document.onmouseup = null;}//获取动作当前坐标
function getClient(e){var coors = 0;if (e.changedTouches){ if(setting.direction == 'X'){coors = e.changedTouches[0].clientX;}else{coors = e.changedTouches[0].clientY;}}else {if(setting.direction == 'X'){
	coors = e.clientX;}else{coors = e.clientY;}}return coors;}}})};})(jQuery);