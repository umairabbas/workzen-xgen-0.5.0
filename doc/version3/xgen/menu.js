/* 
 * home_menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen";

var menu = new Menu(basepath,"Home");
//menu.addMenuItem("Home","../index.html");
menu.addMenuItem("About","xgen/about.html",menuicon);
menu.addMenuItem("Download","xgen/download.html",menuicon);
menu.addMenuItem("License","xgen/GPL_license.html",menuicon);
//menu.addMenuItem("News","xgen/news.html",menuicon);
menu.addMenuItem("QuickStart", "xgen/quickstart.html",menuicon);
subMenu.add(menu);

/* bar and tab are inherited */
print(bar, xgenTab, subMenu);
