/**
 * Application launcher.
 */
//debugger;
// start app function
function startApp() {
    // translate page
	//debugger;
    dwv.i18nPage();

    // main application
    var myapp = new dwv.App();

    // display loading time
    var listener = function (event) {
        if (event.type === "load-start") {
            console.time("load-data");
        }
        else {
            console.timeEnd("load-data");
        }
    };

    // before myapp.init since it does the url load
    myapp.addEventListener("load-start", listener);
    myapp.addEventListener("load-end", listener);

    // also available:
    //myapp.addEventListener("load-progress", listener);
    //myapp.addEventListener("draw-create", listener);
    //myapp.addEventListener("draw-move", listener);
    //myapp.addEventListener("draw-change", listener);
    //myapp.addEventListener("draw-delete", listener);
    //myapp.addEventListener("wl-width-change", listener);
    //myapp.addEventListener("wl-center-change", listener);
    //myapp.addEventListener("colour-change", listener);
    //myapp.addEventListener("position-change", listener);
    //myapp.addEventListener("slice-change", listener);
    //myapp.addEventListener("frame-change", listener);
    //myapp.addEventListener("zoom-change", listener);
    //myapp.addEventListener("offset-change", listener);
    //myapp.addEventListener("filter-run", listener);
    //myapp.addEventListener("filter-undo", listener);

    // initialise the application
    var options = {
        "containerDivId": "dwv",
        "fitToWindow": true,
        "gui": ["tool", "load", "help", "undo", "version", "tags", "drawList"],
        "loaders": [],
        "tools": ["Scroll", "WindowLevel", "Draw", "Filter","FloodfillFactory"],
        "filters": ["Threshold", "Sharpen", "Sobel"],
        "shapes": ["Arrow", "Ruler", "Protractor", "Rectangle", "Roi", "Ellipse", "FreeHand","FreeHandFill","Circle","Eraser"],
        "isMobile": true,
        "helpResourcesPath": "resources/help"
        //"defaultCharacterSet": "chinese"
    };
    myapp.init(options);
    myapp.loadURLs(dicomList);
    readyApp=myapp;
   
    var size = dwv.gui.getWindowSize();
    $(".layerContainer").height(size.height);
}

//与dwv中的dwv.image.PixelBufferDecoder（）一块看，这里指定解析图片的js文件，最重要的是 让系统进行异步解析
// Image decoders (for web workers)
dwv.image.decoderScripts = {
    "jpeg2000": "node_modules/dwv/decoders/pdfjs/decode-jpeg2000.js",
    "jpeg-lossless": "node_modules/dwv/decoders/rii-mango/decode-jpegloss.js",
    "jpeg-baseline": "node_modules/dwv/decoders/pdfjs/decode-jpegbaseline.js"
};

// status flags
var domContentLoaded = false;
var i18nInitialised = false;
// launch when both DOM and i18n are ready
function launchApp() {
	//debugger;
    if ( domContentLoaded && i18nInitialised ) {
        startApp();
    }

}
// i18n ready?
dwv.i18nOnInitialised( function () {
    // call next once the overlays are loaded
    var onLoaded = function (data) {
    	//debugger;
        dwv.gui.info.overlayMaps = data;
        i18nInitialised = true;
        launchApp();
    };
    //debugger;
    // load overlay map info
    $.getJSON( dwv.i18nGetLocalePath("overlays.json"), onLoaded )
    .fail( function () {
        console.log("Using fallback overlays.");
        $.getJSON( dwv.i18nGetFallbackLocalePath("overlays.json"), onLoaded );
    });
});

// check browser support
dwv.browser.check();
// initialise i18n
dwv.i18nInitialise("auto", "node_modules/dwv");

// DOM ready?
//这个地方是整个dwv的开始
$(document).ready( function() {
	//debugger;
    domContentLoaded = true;
    launchApp();
});
