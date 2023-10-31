const path = require("path");
const TSLintPlugin = require("tslint-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const { VueLoaderPlugin } = require("vue-loader");

const webViewConfig = {
	entry: {
		newProject: path.resolve(
			__dirname,
			"src",
			"views",
			"new-project",
			"main.js"
		),
		settings: path.resolve(
			__dirname,
			"src",
			"views",
			"settings",
			"main.js"
		),
	},
	output: {
		path: path.resolve(__dirname, "dist", "views"),
		filename: "[name]-bundle.js",
	},
	module: {
		rules: [
			{
				test: /\.scss$/,
				use: [
					"vue-style-loader",
					"css-loader",
					"sass-loader",
				],
			},
			{
				test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
				type: "asset/resource",
				generator: {
					filename: "fonts/[name][ext]",
				},
			},
			{
				test: /\.vue$/,
				use: "vue-loader",
				exclude: /node_modules/,
			},
			{
				test: /\.css$/,
				use: ["vue-style-loader", "css-loader"],
			},
			{
				test: /\.tsx?$/,
				use: {
					loader: "ts-loader",
					options: {
						appendTsSuffixTo: [/\.vue$/],
					},
				},
				exclude: /node_modules/,
			},
		],
	},
	resolve: {
		extensions: [".ts", ".js", ".vue", ".json"],
		alias: {
			vue: "vue/dist/vue.esm-bundler.js",
		},
		fallback: {
			"os": require.resolve("os-browserify/browser"),
			"path": require.resolve("path-browserify"),
		},
	},
	plugins: [
		new TSLintPlugin({
			files: ["./*.ts"],
		}),
		new VueLoaderPlugin()
	],
	devServer: {
		contentBase: path.join(__dirname),
		compress: true,
		port: 9000,
	},
};

module.exports = [webViewConfig];