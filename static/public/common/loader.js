window.loader = {
    script: function (url, async, callback) {
        if (url) {
            var innerAsync;
            if (typeof(async) === 'function') {
                callback = async;
                innerAsync = false;
            } else {
                innerAsync = async;
            }
            if (typeof(url) === 'string') {
                var script = document.createElement("script");
                script.type = "text/javascript";
                if (typeof(callback) !== 'undefined') {
                    if (script.readyState) {
                        script.onreadystatechange = function () {
                            if (script.readyState == "loaded" || script.readyState == "complete") {
                                script.onreadystatechange = null;
                                callback();
                            }
                        };
                    } else {
                        script.onload = function () {
                            callback();
                        };
                    }
                }
                if (!window.__env || window.__env === 'dev') {
                    script.src = url + '?_=' + new Date().getTime();
                } else if (window.__env === 'run') {
                    script.src = url + '?_=' + __version;
                }
                document.body.appendChild(script);
            } else {
                if (innerAsync) {
                    // 异步加载
                    var count = 0;
                    for (var i=0; i<url.length; i++) {
                        this.script(url[i], innerAsync, function () {
                            count++;
                            if (typeof(callback) === 'function' && count >= url.length) {
                                callback();
                            }
                        });
                    }
                } else {
                    // 顺序加载
                    var loader = this;
                    loader.script(url[0], innerAsync, function () {
                        url.splice(0, 1);
                        if (url.length > 0) {
                            loader.script(url, innerAsync, callback);
                        } else if (typeof(callback) === 'function') {
                            callback();
                        }
                    });
                }
            }
        }
    },
    style: function (url, async, callback) {
        if (url) {
            var innerAsync;
            if (typeof(async) === 'function') {
                callback = async;
                innerAsync = false;
            } else {
                innerAsync = async;
            }
            if (typeof(url) === 'string') {
                var link = document.createElement('link');
                link.type = 'text/css';
                link.rel = 'stylesheet';
                if (typeof(callback) === 'function') {
                    if (link.readyState) {
                        link.onreadystatechange = function () {
                            if (link.readyState == "loaded" || link.readyState == "complete") {
                                link.onreadystatechange = null;
                                callback();
                            }
                        };
                    } else {
                        link.onload = function () {
                            callback();
                        };
                    }
                }
                if (!window.__env || window.__env === 'dev') {
                    link.href = url + '?_=' + new Date().getTime();
                } else if (window.__env === 'run') {
                    link.href = url + '?_=' + __version;
                }
                document.getElementsByTagName("head")[0].appendChild(link);
            } else {
                if (innerAsync) {
                    // 异步加载
                    var count = 0;
                    for (var i=0; i<url.length; i++) {
                        this.style(url[i], innerAsync, function () {
                            count++;
                            if (typeof(callback) === 'function' && count >= url.length) {
                                callback();
                            }
                        });
                    }
                } else {
                    var loader = this;
                    loader.style(url[0], innerAsync, function () {
                        url.splice(0, 1);
                        if (url.length > 0) {
                            loader.style(url, innerAsync, callback);
                        } else if (typeof(callback) === 'function') {
                            callback();
                        }
                    });
                }
            }
        }
    }
};