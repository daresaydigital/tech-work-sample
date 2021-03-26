//
//  DetailViewController.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-26.
//

import UIKit
import RxSwift
import Nuke

final class DetailViewController: UIViewController {

    @IBOutlet weak var cancelButton: UIButton!

    @IBOutlet weak var posterImageView: UIImageView!
    @IBOutlet weak var backgroundImageView: UIImageView!

    @IBOutlet weak var movieName: UILabel!

    @IBOutlet weak var releaseLabel: UILabel!
    @IBOutlet weak var releaseLabelText: UILabel!

    @IBOutlet weak var genreLabel: UILabel!
    @IBOutlet weak var genreLabelText: UILabel!

    @IBOutlet weak var descriptionLabel: UILabel!

    var viewModel: DetailViewModelType!

    private let disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
        //        view.backgroundColor = .red
        setupComponents()
        bindViewModel()
    }

    private func setupComponents() {
        view.bringSubviewToFront(cancelButton)

        cancelButton
            .setImage(UIImage(systemName: "xmark"), for: [.normal])
        cancelButton.tintColor = .black
        cancelButton.setTitle("", for: [.normal])
        cancelButton.rx.tap
            .asObservable()
            .take(1)
            .subscribe(onNext: { [weak self] _ in self?.dismiss(animated: true)})
            .disposed(by: disposeBag)

        releaseLabel.text = "Release Date"
        genreLabel.text = "Genre"
        genreLabelText.numberOfLines = 0
        descriptionLabel.numberOfLines = 0
        backgroundImageView.contentMode = .scaleAspectFill
    }

    private func bindViewModel() {
        let output = viewModel.output

        movieName.text = output.title
        releaseLabelText.text = output.release
        descriptionLabel.text = output.description

        output.genre
            .observe(on: MainScheduler.instance)
            .bind(to: genreLabelText.rx.text)
            .disposed(by: disposeBag)

        /*
         Even though it does not look like it, the image is returned from the cache, if downloaded before.
         */

        let backgroundReq = ImageRequest(url: output.backgroundImageURL, processors: [
                                            ImageProcessors.Resize(size: backgroundImageView.bounds.size)])
        Nuke.loadImage(with: backgroundReq, into: backgroundImageView)

        let posterReq = ImageRequest(url: output.posterImageURL, processors: [ImageProcessors.Resize(size: posterImageView.bounds.size)])
        Nuke.loadImage(with: posterReq, into: posterImageView)

    }

    #if DEBUG
    deinit {
        print("\(self) de-init")
    }
    #endif
}
