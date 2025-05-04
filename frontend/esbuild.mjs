import esbuild from "esbuild";
import {sassPlugin} from "esbuild-sass-plugin";

await esbuild.build({
	entryPoints: ["src/main/scss/*.scss"],
	entryNames: '[dir]/[name].min',
	outdir: "build/dist",
	bundle: true,
	minify: true,
	sourcemap: true,
	external: ['CaskaydiaCove.ttf'],
	plugins: [
		sassPlugin()
	]
})

await esbuild.build({
	entryPoints: ["src/main/ts/*.ts"],
	entryNames: '[dir]/[name].min',
	outdir: "build/dist",
	target: "ES2017",
	bundle: true,
	minify: true,
	sourcemap: true,
})
