import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LOCALSTORAGE_TOKEN_NAME} from '../globals';
import {TokenProviderService} from './service/token.provider.service';
import {UrlService} from './service/url.service';
import {UserProviderService} from './service/user.provider.service';
import {AuthService} from './service/auth.service';
import {Title} from '@angular/platform-browser';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent {

	isLoaded: boolean = false;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private titleService: Title,
		private authService: AuthService,
		private tokenProviderService: TokenProviderService,
		private userProviderService: UserProviderService,
		private urlService: UrlService
	) {
		console.debug('app initiation');

		this.urlService.getViewPath((url) => {
			if (url !== '/') {
				this.activatedRoute.firstChild.data.subscribe(d => {
					console.debug('view: ' + d['title']);
					this.titleService.setTitle(d['title']);
				});
			}

			if (url !== '/auth' && url !== '/register') {
				this.autoLogin(() => {
					if (url === '/') {
						this.router.navigate(['/feed']);
					}
				}, () => {
					this.router.navigate(['/auth']);
				});
			}
		});
	}

	onLoad(loaded: () => void) {
		setTimeout(() => {
			if (this.isLoaded) {
				console.debug('onload: loaded...');
				loaded();
			} else {
				this.onLoad(loaded);
				console.debug('onload: waiting...');
			}
		}, 10);
	}

	private autoLogin(success: () => void, error: () => void): void {
		this.tokenProviderService.token.subscribe(t => {
			if (t) {
				this.isLoaded = true;
				return success();
			}

			console.debug('app: auto login attempt');
			let token = localStorage.getItem(LOCALSTORAGE_TOKEN_NAME);
			if (!token) {
				console.debug('app: no token in localstorage');
				return error();
			}

			console.debug('app: token from localstorage:  ' + token);
			this.authService.validate(token).subscribe(user => {
				console.debug('app: received \'me\'', user);
				this.tokenProviderService.setToken(token);
				this.userProviderService.setMe(user);
				this.isLoaded = true;
				return success();
			}, error2 => {
				console.debug('app: error validating token');
				this.tokenProviderService.setToken(null);
				return error();
			});
		});
	}

}
