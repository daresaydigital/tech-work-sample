//
//  MovieView.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/13/23.
//

import UIKit

class MovieView: UIView {

    // MARK: - SubViews

    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.boldSystemFont(ofSize: 30)
        label.numberOfLines = 0
        label.textAlignment = .center
        return label
    }()

    private lazy var posterImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.isUserInteractionEnabled = false
        return imageView
    }()

    private lazy var genresStackView: UIStackView = {
        let stackView = UIStackView()
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .vertical
        stackView.spacing = 8
        stackView.alignment = .center
        stackView.distribution = .fillEqually
        return stackView
    }()

    private lazy var overviewTextView: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.systemFont(ofSize: 17)
        label.numberOfLines = 0
        return label
    }()

    private lazy var rateAndReleaseDateStackView: UIStackView = {
        let stackView = UIStackView()
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .vertical
        stackView.spacing = 8
        stackView.alignment = .center
        stackView.distribution = .fillEqually
        return stackView
    }()

    private lazy var rateLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.boldSystemFont(ofSize: 20)
        return label
    }()

    private lazy var totalVoteCountLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.systemFont(ofSize: 20)
        return label
    }()

    private lazy var releaseDateLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.systemFont(ofSize: 20)
        return label
    }()

    private lazy var activityIndicator: UIActivityIndicatorView = {
        let activityIndicator = UIActivityIndicatorView(style: .large)
        activityIndicator.translatesAutoresizingMaskIntoConstraints = false
        activityIndicator.hidesWhenStopped = true
        return activityIndicator
    }()

    private lazy var errorLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.systemFont(ofSize: 28)
        label.text = "Error when loading Movie details"
        label.numberOfLines = 0
        label.textAlignment = .center
        return label
    }()

    // MARK: - Initializer

    override init(frame: CGRect) {
        super.init(frame: CGRect())

        setup()
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    // MARK: - View Setup

    private func setup() {
        self.backgroundColor = .systemBackground

        setupTitleLabel()
    }

    private func setupTitleLabel() {
        addSubview(titleLabel)

        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor, constant: 20),
            titleLabel.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 10),
            titleLabel.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -10)
        ])

        setupPosterImage()
    }

    private func setupPosterImage() {
        addSubview(posterImageView)

        NSLayoutConstraint.activate([
            posterImageView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 20),
            posterImageView.centerXAnchor.constraint(equalTo: safeAreaLayoutGuide.centerXAnchor),
            posterImageView.widthAnchor.constraint(equalToConstant: 185),
            posterImageView.heightAnchor.constraint(equalToConstant: 278)
        ])

        setupGenresStackView()
    }

    private func setupGenresStackView() {
        addSubview(genresStackView)

        NSLayoutConstraint.activate([
            genresStackView.topAnchor.constraint(equalTo: posterImageView.bottomAnchor, constant: 20),
            genresStackView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 10),
            genresStackView.widthAnchor.constraint(equalToConstant: 120)
        ])

        setupRateAndReleaseDateStackView()
    }

    private func setupRateAndReleaseDateStackView() {
        addSubview(rateAndReleaseDateStackView)
        rateAndReleaseDateStackView.addArrangedSubview(rateLabel)
        rateAndReleaseDateStackView.addArrangedSubview(totalVoteCountLabel)
        rateAndReleaseDateStackView.addArrangedSubview(releaseDateLabel)

        NSLayoutConstraint.activate([
            rateAndReleaseDateStackView.topAnchor.constraint(equalTo: posterImageView.bottomAnchor, constant: 20),
            rateAndReleaseDateStackView.leadingAnchor.constraint(equalTo: genresStackView.trailingAnchor, constant: 10),
            rateAndReleaseDateStackView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -10)
        ])

        setupOverviewTextView()
    }

    private func setupOverviewTextView() {
        addSubview(overviewTextView)

        NSLayoutConstraint.activate([
            overviewTextView.topAnchor.constraint(equalTo: genresStackView.bottomAnchor, constant: 20),
            overviewTextView.topAnchor.constraint(equalTo: rateAndReleaseDateStackView.bottomAnchor, constant: 20),
            overviewTextView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 10),
            overviewTextView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -10),
            overviewTextView.heightAnchor.constraint(lessThanOrEqualToConstant: 200)
        ])

        setupActivityIndicator()
    }

    private func setupActivityIndicator() {
        addSubview(activityIndicator)

        NSLayoutConstraint.activate([
            activityIndicator.centerXAnchor.constraint(equalTo: safeAreaLayoutGuide.centerXAnchor),
            activityIndicator.centerYAnchor.constraint(equalTo: safeAreaLayoutGuide.centerYAnchor)
        ])

        setupErrorLabel()
    }

    private func setupErrorLabel() {
        addSubview(errorLabel)

        NSLayoutConstraint.activate([
            errorLabel.centerXAnchor.constraint(equalTo: safeAreaLayoutGuide.centerXAnchor),
            errorLabel.centerYAnchor.constraint(equalTo: safeAreaLayoutGuide.centerYAnchor)
        ])
    }

    // MARK: - Renders

    func renderLoadingState() {
        self.titleLabel.isHidden = true
        self.posterImageView.isHidden = true
        self.genresStackView.isHidden = true
        self.overviewTextView.isHidden = true
        self.rateAndReleaseDateStackView.isHidden = true
        self.errorLabel.isHidden = true
        self.activityIndicator.startAnimating()
    }

    func renderErrorState() {
        self.activityIndicator.stopAnimating()
        self.titleLabel.isHidden = true
        self.posterImageView.isHidden = true
        self.genresStackView.isHidden = true
        self.overviewTextView.isHidden = true
        self.rateAndReleaseDateStackView.isHidden = true
        self.errorLabel.isHidden = false
    }

    func renderSuccessState(with viewModel: MovieViewModel) {
        self.activityIndicator.stopAnimating()
        self.errorLabel.isHidden = true
        self.titleLabel.isHidden = false
        self.posterImageView.isHidden = false
        self.genresStackView.isHidden = false
        self.overviewTextView.isHidden = false
        self.rateAndReleaseDateStackView.isHidden = false

        self.titleLabel.text = viewModel.title
        if let url = viewModel.imageUrl {
            self.posterImageView.loadImage(at: url)
        }
        for genre in viewModel.genres {
            let label = UILabel()
            label.translatesAutoresizingMaskIntoConstraints = false
            label.textColor = .label
            label.font = UIFont.systemFont(ofSize: 15)
            label.text = genre
            genresStackView.addArrangedSubview(label)
        }
        self.overviewTextView.text = viewModel.overview
        self.rateLabel.text = "\(viewModel.rate)/10"
        self.totalVoteCountLabel.text = "\(viewModel.totalVoteCount) votes"
        self.releaseDateLabel.text = "Released in \(viewModel.releaseDate)"
    }
}
