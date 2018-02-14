const clearButton = document.querySelector('.collection-item');
let count = 0;

// Action taken when the first task list item is clicked
clearButton.addEventListener('click',function(e){
	e.target.innerText = 'CLICKED!';
	//console.log(`Clicked ${++count} time${count == 1 ? '.' : 's.'}`);
});

// Action taken when the first task list item is hovered over
clearButton.addEventListener('mouseover',function(e){
	e.target.innerText = 'Now click!';
	//console.log(`Clicked ${++count} time${count == 1 ? '.' : 's.'}`);
});

// Action taken when the first task list item is hovered over then out
clearButton.addEventListener('mouseout',function(e){
	e.target.innerText = 'List Item';
	//console.log(`Clicked ${++count} time${count == 1 ? '.' : 's.'}`);
});
/*
document.querySelector('.clear-tasks').addEventListener('click', onClick);

function onClick(e){
  //console.log('Clicked');

  let val;

  val = e;

  // Event target element
  val = e.target;
  val = e.target.id;
  val = e.target.className;
  val = e.target.classList;

  // Event type
  val = e.type;

  // Timestamp
  val = e.timeStamp;

  // Coords event relative to the window
  val = e.clientY;
  val = e.clientX;

  // Coords event relative to the element
  val = e.offsetY;
  val = e.offsetX;

  console.log(val);
}
*/