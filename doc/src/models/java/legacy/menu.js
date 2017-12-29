/* 
 * code menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen [legacy docs]";
var path = basepath + "/xgen/legacy";


var menu = new Menu(path,"Legacy");
//menu.addMenuItem("up", "../index.html",upicon);
menu.addMenuItem("xgen", "index.html",menuicon);
menu.addMenuItem("technical", "technical.html",menuicon);
subMenu.addItem(menu);

/* bar and tab inherited from nav */
print(bar,xgenTab,subMenu);

