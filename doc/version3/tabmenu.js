/* 
 * tabmenu.js 
 * Brad Matlack 4-2004
 * Modify paths as needed.
 */

//var path = basePath;
var path = "/D:/documents/web/workzen/version3";

var icon = "transparent.gif";
var bar = new TabBarUp();
var barMenu;

var homeTab = new Tab();
barMenu = new Menu(path, "Workzen-HG","home/index.html");
barMenu.addMenuItem("Home","home/index.html",icon);
barMenu.addMenuItem("&nbsp;");
homeTab.addMenu(barMenu);

var mvcTab = new Tab();
barMenu = new Menu(path, "Workzen-MVC","mvc/index.html");
barMenu.addMenuItem("Home","mvc/index.html",icon);
barMenu.addMenuItem("Technical","mvc/technical/index.html",icon);
barMenu.addMenuItem("&nbsp;");
mvcTab.addMenu(barMenu);

var eclipseTab  = new Tab();
barMenu = new Menu(path, "Workzen-Plugin","eclipse/index.html");
barMenu.addMenuItem("Home","eclipse/index.html",icon);
barMenu.addMenuItem("Technical","eclipse/technical/index.html",icon);
barMenu.addMenuItem("&nbsp;");
eclipseTab.addMenu(barMenu);

var uiTab  = new Tab();
barMenu = new Menu(path,"Workzen-UI","ui/index.html");
barMenu.addMenuItem("Home","ui/index.html",icon);
barMenu.addMenuItem("Technical","ui/technical/index.html",icon);
uiTab.addMenu(barMenu);

var serviceTab  = new Tab();
barMenu = new Menu(path,"Workzen-Service","service/index.html");
barMenu.addMenuItem("Home","service/index.html",icon);
barMenu.addMenuItem("Technical","service/technical/index.html",icon);
barMenu.addMenuItem("&nbsp;");
serviceTab.addMenu(barMenu);

var commonTab = new Tab();
barMenu = new Menu(path,"Workzen-Common","common/index.html");
barMenu.addMenuItem("Home","common/index.html",icon);
barMenu.addMenuItem("&nbsp;");
commonTab.addMenu(barMenu);

var exampleTab = new Tab();
barMenu = new Menu(path,"Workzen-Example","example/index.html");
barMenu.addMenuItem("Home","example/index.html",icon);
barMenu.addMenuItem("&nbsp;");
exampleTab.addMenu(barMenu);

var xgenTab = new Tab();
barMenu = new Menu(path,"XGen","xgen/index.html");
barMenu.addMenuItem("Home","xgen/index.html",icon);
barMenu.addMenuItem("Technical","xgen/technical/index.html",icon);
barMenu.addMenuItem("Loaders","xgen/loaders/index.html",icon);
//barMenu.addMenuItem("Examples","xgen/code/index.html",icon);
barMenu.addMenuItem("Legacy","xgen/legacy/index.html",icon);
xgenTab.addMenu(barMenu);


bar.addTab(homeTab);
bar.addTab(mvcTab);
bar.addTab(uiTab);
bar.addTab(serviceTab);
bar.addTab(commonTab);
bar.addTab(eclipseTab);
bar.addTab(exampleTab);
bar.addTab(xgenTab);



