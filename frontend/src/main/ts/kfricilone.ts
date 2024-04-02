let darkTheme = false
const themeKey = "kfricilone-theme"

$(() => {
	darkTheme = !isLightPreferred()

	$("#theme-switch").on("click", () => {
		darkTheme = !darkTheme
		setButton()
		setStorage()
		setTheme()
	});

	setButton()

	if (darkTheme) {
		$("body").addClass("dark-theme")
	}

	// @ts-ignore external function
	kard.me.kfricilone.kard.buildKards(darkTheme)
});

const isLightPreferred = (): boolean => {
	const theme = localStorage.getItem(themeKey)
	return theme == null || theme === "light"
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
	// @ts-ignore external function
	kard.me.kfricilone.kard.switchKardTheme($(":root").get(0), darkTheme)

	if (darkTheme) {
		$("body").addClass("dark-theme")
	} else {
		$("body").removeClass("dark-theme")
	}
}
