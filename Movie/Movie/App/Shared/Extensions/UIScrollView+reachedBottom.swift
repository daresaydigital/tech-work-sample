#if os(iOS)
import UIKit
import RxSwift
import RxCocoa

public extension Reactive where Base: UIScrollView {
	/**
	Shows if the bottom of the UIScrollView is reached.
	- parameter offset: A threshhold indicating the bottom of the UIScrollView.
	- returns: ControlEvent that emits when the bottom of the base UIScrollView is reached.
	*/
	func reachedBottom(offset: CGFloat = 0.0) -> ControlEvent<Void> {
		let source = contentOffset.map { contentOffset in
			let visibleHeight = self.base.frame.height - self.base.contentInset.top - self.base.contentInset.bottom
			let yAxis = contentOffset.y + self.base.contentInset.top
			let threshold = max(offset, self.base.contentSize.height - visibleHeight)
			return yAxis >= threshold
		}
		.distinctUntilChanged()
		.filter { $0 }
		.map { _ in () }
		return ControlEvent(events: source)
	}
}
#endif
