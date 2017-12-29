/* 
 * code menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen";
var path = basepath + "/xgen/technical";
var menu = new Menu(path,"Technical");

//menu.addMenuItem("Code Examples","code/examples.html");
//menu.addMenuItem("&nbsp;");

menu.addMenuItem("Architecture","architecture.html",menuicon);
menu.addMenuItem("Components","components.html",menuicon);
menu.addMenuItem("Configuration","configuration.html",menuicon);
///menu.addMenuItem("Loaders","loader.html",menuicon);
subMenu.add(menu);

/* bar and tab inherited from nav */
print(bar, xgenTab, subMenu);
