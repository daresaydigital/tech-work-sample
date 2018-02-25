/* This module is not mine, it's based on the original "Smooth Scroll to" module by
@hilliu. I modified it to allow scrolling on two axis. Maybe I'll make a pull request...
See https://www.npmjs.com/package/smooth-scroll-to, for license see https://spdx.org/licenses/MIT.html  */
'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _getScrollInfo = require('get-scroll-info');

var _easeInOutCubic = require('easing-lib/easeInOutCubic');

var _easeInOutCubic2 = _interopRequireDefault(_easeInOutCubic);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var isRunning = false;

/**
 *  !!Important!! any logic change need take care isRunning
 */
var smoothScrollTo = function smoothScrollTo(x, y, duration, el, callback) {
    if (isRunning) {
        if ('function' === typeof callback) {
            callback();
        }
        return false;
    } else {
        isRunning = true;
    }
    el = (0, _getScrollInfo.getScrollNode)(el);
    if (!duration) {
        duration = 900;
    }
    var fromX = el.scrollLeft;
    var fromY = el.scrollTop;

    var goX = x - fromX;
    var goY = y - fromY;
    if (!goX && !goY) {
        isRunning = false;
        if ('function' === typeof callback) {
            callback();
        }
        return;
    }
    var beginTimeStamp = void 0;
    var scrollTo = function scrollTo(timeStamp) {
        beginTimeStamp = beginTimeStamp || timeStamp;
        var elapsedTime = timeStamp - beginTimeStamp;
        var progressX = (0, _easeInOutCubic2.default)(elapsedTime, fromX, goX, duration);
        var progressY = (0, _easeInOutCubic2.default)(elapsedTime, fromY, goY, duration);
        el.scrollLeft = progressX;
        el.scrollTop = progressY;
        if (elapsedTime < duration && (goX || goY)) {
            requestAnimationFrame(scrollTo);
        } else {
            isRunning = false;
            if ('function' === typeof callback) {
                callback();
            }
        }
    };
    requestAnimationFrame(scrollTo);
};

exports.default = smoothScrollTo;
module.exports = exports['default'];
