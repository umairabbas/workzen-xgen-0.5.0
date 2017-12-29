/* 
 * code menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen";
var path = basepath + "/xgen/loaders";


var menu = new Menu(path,"Loaders");
menu.addMenuItem("JavaModel", "java/index.html",menuicon);
menu.addMenuItem("TextFile", "textfile.html",menuicon);
menu.addMenuItem("XmlFile", "xmlfile.html",menuicon);
subMenu.addItem(menu);

/* bar and tab inherited from nav */
print(bar,xgenTab,subMenu);

