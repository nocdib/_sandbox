module.exports = {

    // Base message for all routes
    baseMiddleware(req, res, next) {
        req.message = 'This middleware runs for every route';
        next();
    },

    // Message only for /example
    baseExampleMiddleware(req, res, next) {
        req.message += '<br>This middleware only runs from /example<br>';
        next();
    },

    // Message for all /people routes
    peopleMiddleware(req, res, next) {
        req.message += '<br>This middleware only runs from /people<br>';
        next();
    },

}


