const {app, BrowserWindow} = require('electron');
const path = require('path');
const url = require('url');
let win;

function createWindow() {
	win = new BrowserWindow({});
	// load the dist folder from Angular
	win.loadURL(url.format({
		pathname: path.join(__dirname, 'dist/project-tracker/index.html'),
		protocol: 'file:',
		slashes: true
	}));
	win.maximize();
	// Open the DevTools optionally:
	win.webContents.openDevTools();
	win.on('closed', () => {
		win = null;
	})
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
	if (process.platform !== 'darwin') {
		app.quit()
	}
});

app.on('activate', () => {
	if (win === null) {
		createWindow()
	}
});

// how to here https://stackoverflow.com/a/49812397/8662097
