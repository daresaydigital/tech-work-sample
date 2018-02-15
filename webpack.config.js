const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  // Entry files for our popup and background pages
  entry: {
    background: './src/background.js',
    newtab: './src/newtab.js',
  },
  /* Extension will be built into ./dist folder, which can
  then be loaded as unpacked extension in Chrome */
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].bundle.js',
  },
  // Here we define loaders for different file types
  module: {
    rules: [
      // We use Babel to transpile JSX
      {
        test: /\.js$/,
        include: [
          path.resolve(__dirname, './src'),
        ],
        use: 'babel-loader',
      },
      {
        test: /\.css$/,
        loader: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'resolve-url-loader'],
        }),
      },
      {
        test: /\.(ico|eot|otf|webp|ttf|woff|woff2)(\?.*)?$/,
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
    ],
  },
  plugins: [
    // create CSS file with all used styles
    new ExtractTextPlugin('bundle.css'),
    // create popup.html from template and inject styles and script bundles
    new HtmlWebpackPlugin({
      inject: true,
      chunks: ['newtab'],
      filename: 'newtab.html',
      template: './src/template.html',
    }),
    // copy extension manifest and icons
    new CopyWebpackPlugin([
      { from: './src/manifest.json' },
      { context: './src/assets', from: 'weatherwise-logo.png', to: 'assets' },
    ]),
  ],
};
