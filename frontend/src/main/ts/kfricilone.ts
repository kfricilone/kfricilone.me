let darkTheme: boolean = false
const themeKey: string = "kfricilone-theme"

$(function () {
	darkTheme = !isLightPreferred()

	$("#theme-switch").on("click", function () {
		darkTheme = !darkTheme
		setButton()
		setStorage()
		setTheme()
	});

	setButton()

	if (darkTheme) {
		$("body").addClass("dark-theme")
	}

	// @ts-ignore
	kard.buildGhCards(darkTheme)
});

const isLightPreferred = (): boolean => {
	let theme = localStorage.getItem(themeKey)
	return theme == null || theme == "light"
}

const setButton = () => {
	if (darkTheme) {
		$("#sun").hide()
		$("#moon").show()
	} else {
		$("#sun").show()
		$("#moon").hide()
	}
}

const setStorage = () => {
	localStorage.setItem(themeKey, darkTheme ? "dark" : "light")
}

const setTheme = () => {
	// @ts-ignore
	kard.switchGhTheme($(":root").get(0), darkTheme)
	if (darkTheme) {
		$("body").addClass("dark-theme")
	} else {
		$("body").removeClass("dark-theme")
	}
}
