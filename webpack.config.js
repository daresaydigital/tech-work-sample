const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const ExtractMainCSS = new ExtractTextPlugin('bundle.css');
const ExtractContentCSS = new ExtractTextPlugin('content.bundle.css');

module.exports = {
  /* Entry files for newtab and background pages. A popup page could also be included */
  entry: {
    // background: './src/background.js',
    newtab: './src/newtab.js',
    content: './src/content.js',
    background: ['babel-polyfill', './src/background.js'],
  },
  /* Extension will be built into ./dist folder, which can
  then be loaded as unpacked extension in Chrome */
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].bundle.js',
  },
  /* Different loaders for different file types... */
  module: {
    rules: [
      /* Use babel to transpile JSX */
      {
        test: /\.js$/,
        include: [
          path.resolve(__dirname, './src'),
          /* Apparently I neeed to transpile the react-geocode module ¯\_(ツ)_/¯ */
          path.resolve(__dirname, './node_modules/react-geocode'),
        ],
        use: 'babel-loader',
      },
      {
        test: /\.css$/,
        exclude: path.resolve(__dirname, './src/content.css'),
        loader: ExtractMainCSS.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'resolve-url-loader'],
        }),
      },
      {
        test: /content\.css/,
        loader: ExtractContentCSS.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'resolve-url-loader'],
        }),
      },
      {
        test: /\.(ico|eot|otf|webp|ttf)(\?.*)?$/,
        use: 'file-loader?limit=100000',
      },
      {
        test: /\.(jpe?g|png|gif)$/i,
        loader: 'file-loader?limit=1024&name=assets/[name].[ext]',
      },
      {
        test: /\.svg$/,
        loader: 'svg-inline-loader',
      },
      {
        test: /\.woff?2$/,
        loader: 'file-loader?limit=1024&name=assets/fonts/[name].[ext]',
      },
    ],
  },
  plugins: [
    /* Create a CSS file with all used styles except content ones */
    ExtractMainCSS,
    /* Create a CSS file with content styles */
    ExtractContentCSS,
    /* Create newtab.html from template and inject styles and script bundles */
    new HtmlWebpackPlugin({
      inject: true,
      chunks: ['newtab'],
      filename: 'newtab.html',
      template: './src/template.html',
    }),
    /* Copy manifest file and icons */
    new CopyWebpackPlugin([
      { from: './src/manifest.json' },
      { context: './src/assets', from: 'weatherwise-logo.png', to: 'assets' },
      { context: './src/assets/fonts', from: 'Barlow Regular.woff2', to: 'assets/fonts' },
      { context: './src/assets/fonts', from: 'Quicksand Regular.woff2', to: 'assets/fonts' },
    ]),
  ],
};
