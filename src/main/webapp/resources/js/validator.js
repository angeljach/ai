//---|| Validación de versiones de Browser soportadas.

//http://www.w3schools.com/js/js_browser.asp
//txt = "<p>Browser CodeName: " + navigator.appCodeName + "</p>";
//txt+= "<p>Browser Name: " + navigator.appName + "</p>";
//txt+= "<p>Browser Version: " + navigator.appVersion + "</p>";
//txt+= "<p>Cookies Enabled: " + navigator.cookieEnabled + "</p>";
//txt+= "<p>Platform: " + navigator.platform + "</p>";
//txt+= "<p>User-agent header: " + navigator.userAgent + "</p>";

brwName = navigator.appName;
brwVers = navigator.appVersion;
brwUsrAgent = navigator.userAgent;
fCompatible = true;
strMsgComp = "";
urlDwnl = "logout.xhtml";


//---|| Opera
//Browser Name: Opera
//Browser Version: 9.80 (X11; Linux i686; U; en)
//User-agent header: Opera/9.80 (X11; Linux i686; U; en) Presto/2.9.168 Version/11.50
if (brwName == "Opera") {
    brwVers = brwVers.substring(0, brwVers.indexOf(" "));
} else if(brwName == "Microsoft Internet Explorer") {
    brwVers = brwVers.substring(brwVers.indexOf("MSIE") + 5);
    brwVers = brwVers.substring(0, brwVers.indexOf(";"));

    if (parseFloat(brwVers) < 9.0) {
        fCompatible = false;
        strMsgComp = "La versión de Opera " + brwVers + " no es soportada. Debes utilizar Opera 9.0 o superior.";
        urlDwnl = "http://www.opera.com/download/";
    }

    if (parseInt(brwVers) < 7) {
        fCompatible = false;
        strMsgComp = "Internet Explorer " + brwVers + " no esta soportado.\nDebes utilizar IE 7 o superior.";
        urlDwnl = "http://windows.microsoft.com/en-US/internet-explorer/products/ie/home";
    }
} else if(brwName == "Netscape") {    
    if (brwVers.indexOf("Chrome/") != -1) {
        //---|| Validanto Chrome
        brwVers = brwVers.substring(brwVers.indexOf("Chrome/") + 7);
        brwVers = brwVers.substring(0, brwVers.indexOf("."));

        if (parseInt(brwVers) < 10) {
            fCompatible = false;
            strMsgComp = "Google Chrome " + brwVers + " no esta soportado. Debes utilizar Google Chrome 10 o superior.";
            urlDwnl = "http://www.google.com/chrome";
        }
    } else if(brwVers.indexOf("Chromium/") != -1) {
        //---|| Validanto Chromium
        brwVers = brwVers.substring(brwVers.indexOf("Chromium/") + 9);
        brwVers = brwVers.substring(0, brwVers.indexOf("."));

        if (parseInt(brwVers) < 10) {
            fCompatible = false;
            strMsgComp = "Chromium " + brwVers + " no es soportado. Debes utilizar Chromium 10 o superior.";
            urlDwnl = "http://www.chromium.org/";
        }
    } else if(brwUsrAgent.indexOf("Firefox/") != -1) {
        //---|| Validanto Firefox
        brwVers = brwUsrAgent.substring(brwUsrAgent.indexOf("Firefox/") + 8);

        if (parseFloat(brwVers) < 3.6) {
            fCompatible = false;
            strMsgComp = "La versión de Firefox " + brwVers + " no es soportada. Debes utilizar Firefox 3.6 o superior.";
            urlDwnl = "http://www.mozilla.com/";
        }
    } else {
        strMsgComp = "Tu Browser no está certificado para el uso de esta herramienta. Favor de contactar a soporte_h2h@bmvcorp.com.mx para validarlo.";
    }
}


//Browser Name: Microsoft Internet Explorer
//Browser Version: 4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET4.0C; .NET CLR 2.0.50727)
//User-agent header: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET4.0C; .NET CLR 2.0.50727)


//---|| Firefox
//Browser Name: Netscape
//Browser Version: 5.0 (X11)
//User-agent header: Mozilla/5.0 (X11; Linux i686; rv:5.0) Gecko/20100101 Firefox/5.0


//---|| Internet Explorer
//Browser Name: Microsoft Internet Explorer
//Browser Version: 4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; InfoPath.2; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; OfficeLiveConnector.1.5; OfficeLivePatch.1.3; .NET4.0C; .NET4.0E)
//User-agent header: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; InfoPath.2; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; OfficeLiveConnector.1.5; OfficeLivePatch.1.3; .NET4.0C; .NET4.0E)

//---|| Chrome
//Browser Name: Netscape
//Browser Version: 5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30
//User-agent header: Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30

//---|| Chromium
//Browser Name: Netscape
//Browser Version: 5.0 (X11; Linux i686) AppleWebKit/534.30 (KHTML, like Gecko) Ubuntu/10.10 Chromium/12.0.742.112 Chrome/12.0.742.112 Safari/534.30
//User-agent header: Mozilla/5.0 (X11; Linux i686) AppleWebKit/534.30 (KHTML, like Gecko) Ubuntu/10.10 Chromium/12.0.742.112 Chrome/12.0.742.112 Safari/534.30

if (fCompatible == false) {
    alert (strMsgComp);
    window.location.href = urlDwnl
}


//document.write('Testing');