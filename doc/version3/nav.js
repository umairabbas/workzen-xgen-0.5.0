/* 
 * nav.js 
 * Copyright 10-2004 Brad Matlack matlackb@sourceforge.net
 * License http://www.gnu.org/copyleft
 * DISCLAIMER: THESE JAVASCRIPT FUNCTIONS ARE SUPPLIED 'AS IS', WITH 
 * NO WARRANTY EXPRESSED OR IMPLIED. YOU USE THEM AT YOUR OWN RISK. 
 *
 * Modify title, and basepath as needed.
 * Each directory of the webtree should contain a header.js, footer.js and submenu.js.
 * Each html page should include a header and footer script:
 * <script language="javascript" type="text/javascript" src="header.js"></script>
 * <script language="javascript" type="text/javascript" src="footer.js"></script>
 *
 * Example header.js points to upstream scripts plus the (directory) submenu.js
 * document.write("<script language=javascript type=text/javascript src=../nav.js></script>");
 * document.write("<script language=javascript type=text/javascript src=../tabmenu.js></script>");
 * document.write("<script language=javascript type=text/javascript src=submenu.js></script>");
 *
 * Example footer.js pointing to upstream script
 * document.write("<script language=javascript type=text/javascript src=../footer.js></script>");
 *
 * Example top level footer.js
 * handleRightMenu();
 * document.write("Copyright © someone.us 2004");
 *
 * Provide your own "tabmenu.js" to provide TabBar navigation.
 * Provide your own "submenu.js" and call print(bar, tab, menu); 
 *
 * Example tabmenu.js
 * var path = "/D:/documents/web/project";
 * var icon = "transparent.gif";
 * var bar = new TabBarUp();
 * var homeTab = new Tab();
 * var barMenu = new Menu(path, "Home","index.html");
 * barMenu.addMenuItem("Home","home/index.html",icon);
 * barMenu.addMenuItem("&nbsp;");
 * homeTab.addMenu(barMenu);
 * bar.addTab(homeTab);
 *
 * Example submenu.js
 * var menu = new Menu(basepath,"Home");
 * menu.addMenuItem("License","home/GPL_license.html",menuicon);
 * menu.addMenuItem("About","home/about.html",menuicon);
 * menu.addMenuItem("Download","home/download.html",menuicon);
 *
 * Each submenu.js calls the print method, using inherited "bar" and "tab" parameters
 * print(bar, homeTab, menu);
 */

var title = "workzen";
////var basepath = "/D:/documents/web/workzen/version3";
var basepath = "D:/Project/workzen.xgen/doc/version3";
//var basepath = "/workzen/docs";
//var menuicon = "right.gif";
//var menuicon = "navArrowRight.gif";
var menuicon = "fw3.gif";
var upicon = "up_nav.gif";
var showDebug = false;
var version = navigator.appVersion;
var isNav = (navigator.appName == "Netscape");
var isIE  = (navigator.appName.indexOf("Microsoft") != -1);
var cookieDomain = "";
var project = "Workzen"; // page header
var customHtml = new Container(); // global custom html container
var subMenu = new Container(); // global submenu container
  
//=================== tabbar objects =================================
function TabBarUp(){
  this.tabs = new Array();
  this.addTab = addTabMethod;
  this.getBar = getTabBarUpMethod;
}

// tabbar object  
function TabBarDown(){
  this.tabs = new Array();
  this.addTab = addTabMethod;
  this.getBar = getTabBarDownMethod;
}

// tabbar object  
function TabBarMiddle(){
  this.tabs = new Array();
  this.addTab = addTabMethod;
  this.getBar = getTabBarMiddleMethod;
}

// tabbar method  
function addTabMethod(tab){
  index = this.tabs.length;
  this.tabs[index] = tab;
}

// tabbar method
function getTabBarUpMethod(selected){
  debug("getTabBarUp()");
  html = "<div class='tabbar' >";
  // tabs
  for( var i=0; i < this.tabs.length; i++ ){
   var tab = this.tabs[i];
   css = "upTab";
   if( tab == selected ){
     css = "selectedUpTab";
   }
   html += "<span class='" + css + "'>" + tab.menu.title.getAddress(css) + "</a></span>";
  }
  html += getSpanMenu(selected.menu);
  html += "</div>";
  return html;
}

// tabbar method
function getTabBarDownMethod(selected){
  debug("getTabBarDown()");
  html = "<div class='tabbar'>";
  html += getSpanMenu(selected.menu)
  //html += "<div class='bar'></div>";
  for( var i=0; i < this.tabs.length; i++ ){
   var tab = this.tabs[i];
   css = "downTab";
   if( tab == selected ){
     css = "selectedDownTab";
   }
   html += "<span class='" + css + "'>" + tab.menu.title.getAddress(css) + "</span>";
   html += "</div>";
  }
  return html;
}

// tabbar method
function getTabBarMiddleMethod(selected){
  debug("getTabBarMiddle()");
  html = "";
  html += "<div class='bar'>";
  for( var i=0; i < this.tabs.length; i++ ){
   var tab = bar.tabs[i];
   css = "middleTab";
   if( tab == selected ){
     css = "selectedMiddleTab";
   }
   html += "<span class='" + css + "'>" + tab.menu.title.getAddress(css) + "</span>";
  }
  html += "</div>";
  html += getSpanMenu(selected.menu);
  return html;
}

//===================== tab object ==================================
function Tab(){
  this.menu;
  this.addMenu = addMenuMethod;
}

// tab method
function addMenuMethod(menu){
	 this.menu = menu;
}
 
//===================== menu object =================================
function Menu(path, name, address, gif){
	this.path = path;
  this.title = new MenuItem(path, name, address, gif);
  this.items = new Array();
  this.addMenuItem = addMenuItemMethod;
}

// menu method  
function addMenuItemMethod(name, address, gif){
  var item = new MenuItem(this.path, name, address, gif);
  index = this.items.length;
  this.items[index] = item;
}

//==================== menuitem object ===============================
function MenuItem(path, name, filename, gif){
	this.path = path;
  this.name = name;
  this.gif = gif;
  this.filename = "";
  if( filename )
  	this.filename = filename;
  this.getAddress = getAddressMethod;
  this.getDiv = getMenuItemDivMethod;
  this.getSpan = getMenuItemSpanMethod;
}

// menuitem method
function getAddressMethod(styleID){
	 html = "unknown";
	 if( this != null ){
	 	 address = this.path + "/" + this.filename;
	 	 html = "<a href='" + address + "' id='" + styleID + "'>" + this.name + "</a>";
	 }
	 return html;
}

// build a bar from the tab menu
function getSpanMenu(menu){
  debug("getSpanMenu()");
  html = "";
  html = "<div class='bar'>";
  for( var i=0; i < menu.items.length; i++ ){
    var item = menu.items[i];
    html += item.getSpan();
  }
  html += "</div>";
  return html;
}

// menuitem method
function getMenuItemDivMethod(){
	 image = "";
	 if( this.gif ){
    image = getImage(basepath, this.gif);
    image += "&nbsp;";
	 }
  html = "";
  if( this.filename ){
    html += "<div class='menuitem'>" + image + this.getAddress("menuitemID") + "</div>";
  }else{
 	  html += "<div class='menuitem'>" + this.name + "</div>";
  }
  return html;
}

// menuitem method 
function getMenuItemSpanMethod(){
  image = "&nbsp;&nbsp;";
	 if( this.gif ){
	    image = getImage(basepath, this.gif);
	 }
  html = "";
  if( this.filename ){
    html += "<span class='menuitem'>" + image + this.getAddress() + "</span>";
  }else{
 	  html += "<span class='menuitem'>" + this.name + "</span>";
  }
  return html;
}

//================= html submenu functions ====================================  
// html function  
function getTabbedSubMenuUp(menu){
  debug("getTabbedSubMenu()");
  html = "";
  html += "<div class='menux'>";
  //html += "<div>"; 
  html += "<span class='selectedUpTab'>" + menu.title.name + "</span>";
  //html += "</div>";
  html += "<div class='box'>";
  html += "<div class='box-body'>";
  for( var i=0; i < menu.items.length; i++ ){
   var item = menu.items[i];
   html += item.getDiv();
  }
  html += "</div>";
  html += "</div>";
  html += "</div>";
  return html;
}

// html function  
function getTabbedSubMenuDown(menu){
  debug("getTabbedSubMenuDown()");
  html = "";
  html  = "<div class='menux'>";
  html += "<div class='box'>";
  html += "<div class='box-body'>";
  for( var i=0; i < menu.items.length; i++ ){
   var item = menu.items[i];
   html += item.getDiv();
  }
  html += "</div>";
  html += "</div>";
  html += "<div class=''>&nbsp;"; // microsoft bug
  html += "<span class='selectedDownTab'>" + menu.title.name + "</span>";
  html += "</div>";
  html += "</div>";
  return html;
}

// html function  
function getTitledSubMenu(menu, showTitle){
  debug("getTitledSubMenu()");
  html  = "<div class='menux'>";
  if( showTitle){
    html += "<div class='box-title'>" + menu.title.name + "</div>";
  }
  html += "<div class='box'>";
  html += "<div class='box-body'>";
  for( var i=0; i < menu.items.length; i++ ){
   var item = menu.items[i];
   html += item.getDiv();
  }
  html += "</div>";
  html += "</div>";
  html += "</div>";
  return html;
}


//========================== Container ===========================
// container object  
function Container(){
  this.items = new Array();
  this.addItem = addItemMethod;
  this.add = addItemMethod;
  //this.getHtml = getHtmlMethod;
}
// generic add method for container
function addItemMethod(item){
  index = this.items.length;
  this.items[index] = item;
}

// container method, wraps each item in a styled div
function getCustomHtml(container, css){
  debug("getHtmlMethod()");
  html = "<p>";
  for( var i=0; i < container.items.length; i++ ){
    var item = container.items[i];
    html += "<div class='" + css + "'>";
    html += item;
    html += "</div>";
  }
  html += "</p>";
  return html;
}

// html function
/*
function getCustomHtml(){
	 html = "<p>";
	 html += custom.getHtml("custom");
	 html += "</p>";
	 return html;
}*/
/*
function getCustomMenu(container, menuName){
  debug("getCustomMenu()");
  css = "box";
  html = "";
  for( var i=0; i < container.items.length; i++ ){
    var menu = container.items[i];
    title = menu.title.name;
    if( title == menuName ){
      html += getTitledSubMenu(menu,true);
      html += getCustomHtml(custom, "custom");
    }else{
      html += "<div class='" + css + "'>";
      html += menu.title.name;
      html += "</div>";
    }
  }
  return html;
}*/

//================ CALLBACK from menu.js ============================
function printxxx(bar, selectedTab, menu){
 
  document.write(bar.getBar(selectedTab));
  menuLocation = retrieveCookie("menuLocation");
  //menuLocation = "right";
 
  html = "";
  html += "<table cellpadding='10'>";
  html += "<tr>";
 
  if( menuLocation == "right" ){
    html += "<td valign='top' width='100%' class='body'>";
    html += "<h1>" + project + "</h1>";
    //alert("menu: right");
    // body
  }else{
    html += "<td valign='top'>";
    //html += getTabbedSubMenuUp(menu);
    //html += getTabbedSubMenuDown(menu);
    html += getTitledSubMenu(menu,true);
    html += getCustomHtml(customHtml, "custom");
    html += "</td>";
    html += "<td valign='top' class='body'>";
    html += "<h1>" + project + "</h1>";
    //alert("menu: left");
    // body
  }
  document.write(html);
}

//================ CALLBACK from menu.js ============================
function print(bar, selectedTab, menuContainer){
 
  document.write(bar.getBar(selectedTab));
  menuLocation = retrieveCookie("menuLocation");
  //menuLocation = "right";
 
  html = "";
  html += "<table cellpadding='10'>";
  html += "<tr>";
  if( menuLocation == "right" ){
      html += "<td valign='top' width='100%' class='body'>";
      html += "<h1>" + project + "</h1>";
      document.write(html);
      return;
      //alert("menu: right");
      // body
  }
  
  html += "<td valign='top'>";    
  for( var i=0; i < menuContainer.items.length; i++ ){
    var menu = menuContainer.items[i];
  
   
      //html += getTabbedSubMenuUp(menu);
      //html += getTabbedSubMenuDown(menu);
      html += getTitledSubMenu(menu,true);
      html += "<div>&nbsp;</div>";
  }
  html += getCustomHtml(customHtml, "custom");
  html += "</td>";
  html += "<td valign='top' class='body'>";
  html += "<h1>" + project + "</h1>";
  //alert("menu: left");
  // body
  document.write(html);
}

//=================== CALLBACK from footer.js ========================
function handleRightMenu(){
  menuLocation = retrieveCookie("menuLocation");
  //menuLocation = "right";
  //document.write(subMenu.items.length);
  
  if( menuLocation == "right" ){
    
    //alert("footer: right");
    html = "</td><td valign='top'>";
    //document.write(getTitledSubMenu(menu,true));
    //document.write(getCustomHtml(customHtml,"custom"));
     for( var i=0; i < subMenu.items.length; i++ ){
        var menu = subMenu.items[i];
        //html += getTabbedSubMenuUp(menu);
        //html += getTabbedSubMenuDown(menu);
        html += getTitledSubMenu(menu,true);
        html += "<div>&nbsp;</div>";
    }
    html += getCustomHtml(customHtml, "custom");
    document.write(html);
    document.write("</td></tr></table>");
    //document.write("xxx");
  }else{
    //alert("footer: left");
    document.write("</td></tr></table>");
  }
}

//=================== COOKIES =========================================

//http://www.howtocreate.co.uk/tutorials/jsexamples/cookies.html
function retrieveCookie( cookieName ) {
	/* 
	retrieved in the format
	cookieName4=value; cookieName3=value; cookieName2=value; cookieName1=value
	only cookies for this domain and path will be retrieved 
	*/
	var cookieJar = document.cookie.split( "; " );
	for( var x = 0; x < cookieJar.length; x++ ) {
		var oneCookie = cookieJar[x].split( "=" );
		if( oneCookie[0] == escape( cookieName ) ) { 
		  return unescape( oneCookie[1] ); 
		  }
	}
	return null;
}
//http://www.howtocreate.co.uk/tutorials/jsexamples/cookies.html
function setCookie( cookieName, cookieValue, lifeTime, path, domain, isSecure ) {
	if( !cookieName ) { return false; }
	if( lifeTime == "delete" ) { lifeTime = -10; } //this is in the past. Expires immediately.
	/* 
	This next line sets the cookie but does not overwrite other cookies.
	syntax: cookieName=cookieValue[;expires=dataAsString[;path=pathAsString[;domain=domainAsString[;secure]]]]
	Because of the way that document.cookie behaves, writing this here is equivalent to writing
	document.cookie = whatIAmWritingNow + "; " + document.cookie; 
	*/
	document.cookie = escape( cookieName ) + "=" + escape( cookieValue ) +
		( lifeTime ? ";expires=" + ( new Date( ( new Date() ).getTime() + ( 1000 * lifeTime ) ) ).toGMTString() : "" ) +
		( path ? ";path=" + path : "") + ( domain ? ";domain=" + domain : "") + 
		( isSecure ? ";secure" : "");
	//check if the cookie has been set/deleted as required
	if( lifeTime < 0 ) { if( typeof( retrieveCookie( cookieName ) ) == "string" ) { return false; } return true; }
	if( typeof( retrieveCookie( cookieName ) ) == "string" ) { return true; } return false;
}

function setMenuCookie(side){
  cookieName = "menuLocation";
  cookieValue = side;
  lifeTime = 0;
  path = "/";
  domain = cookieDomain;
  isSecure = "";
  if( setCookie( cookieName, cookieValue, lifeTime, path, domain, isSecure ) ){
    menuLocation = retrieveCookie(cookieName);
    //alert("MenuLocation: " + menuLocation);
    reload();
  }else{
    alert("Unable to set menu location cookie for domain:[" + domain + "]");
  }
}

//====================================================================

function debug(str){
	 if( showDebug )
	   document.writeln(str);
}

function getImage(path, address){
  return "<img src='" + path + "/images/" + address + "'/>";
}

function getDate(date){
  var day = date.getDate();
  var mon = date.getMonth();
  var year = date.getYear();
  if( year < 1900 ){
    year = year + 1900;
  }
  return mon + "-" + day + "-" + year;
}

function reload(){
  sURL = unescape(window.location.pathname);
  window.location.reload( false );
}

//======================= custom html ====================================//
var sfimage = "";
sfimage += "<a href='http://sourceforge.net/projects/workzen'>";
sfimage += "<img src='http://sourceforge.net/sflogo.php?group_id=87769&amp;type=2' border='0'/>";
sfimage += "</a>";
//custom.addItem("hello world");
customHtml.addItem(sfimage);
//custom.addItem(eclipse);

//========================= HEAD ========================================//
var head = "";
head += "<head>";
head += "<title>" + title + "</title>";
head += "<link type='text/css' href='" + basepath + "/print.css' rel='stylesheet' media='print'/>";
head += "<link type='text/css' href='" + basepath + "/style.css' rel='stylesheet' media='screen'/>";
head += "<link type='text/css' href='" + basepath + "/box.css'   rel='stylesheet' media='screen'/>";
head += "<link type='text/css' href='" + basepath + "/box.css'   rel='stylesheet' media='print'/>";
//head += "<script language='javascript' type='text/javascript' src='" + basepath + "/tabmenu.js'></script>";
head += "</head>";
head += "<body>";
head += "<div class=topBorder>&nbsp;" + getDate(new Date()) + "&nbsp;";
head += "<img src='" + basepath + "/images/menu_left.gif' alt='menu left' onclick=\"setMenuCookie('left');\">";
head += "<img src='" + basepath + "/images/menu_right.gif' alt='menu right' onclick=\"setMenuCookie('right');\">";
head += "</div>";
head += "<div class=headerTitle>&nbsp; &lt;" + title + "&gt;</div>";
//========================================================================//
document.write(head);



