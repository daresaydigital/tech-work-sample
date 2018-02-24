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
var smoothScrollTo = function smoothScrollTo(to, duration, el, direction, callback) {
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
    var from = direction === 'vertical' ? el.scrollTop : el.scrollLeft;
    var go = to - from;
    if (!go) {
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
        var progress = (0, _easeInOutCubic2.default)(elapsedTime, from, go, duration);
        el.scrollTop = progress;
        el.scrollLeft = progress;
        if (elapsedTime < duration && go) {
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
