const path = require('path');

module.exports = {
    entry: './app.js',  // This should be relative to the webpack.config.js location
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: path.resolve(__dirname, '../resources/static/built'),
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }
            }
        ]
    }
};