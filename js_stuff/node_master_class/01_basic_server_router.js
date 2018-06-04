module.exports ={

    routes(data) {
        const path = data.path;

        if(new RegExp('^\/*foo\/bar\/*$').test(path)) { // '/foo/bar/'
            this.fooBarHandler();
        } else if(new RegExp('^\/*foo\/*$').test(path)) { // '/foo/':
            this.fooHandler();
        } else if(new RegExp('^\/*bar\/*$').test(path)) { // '/bar/':
            this.barHandler();
        } else {
            this.notFoundHandler();
        }
    },
    fooBarHandler() { console.log('fooBarHandler'); },
    fooHandler() { console.log('fooHandler'); },
    barHandler() { console.log('barHandler'); },
    notFoundHandler() { console.log('notFoundHandler'); },
}

