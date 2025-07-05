import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from '../router/app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptorInterceptor } from '../interceptor/token-interceptor-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient( withInterceptors([tokenInterceptorInterceptor])),
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideRouter(routes), provideClientHydration(withEventReplay())
  ]
};
