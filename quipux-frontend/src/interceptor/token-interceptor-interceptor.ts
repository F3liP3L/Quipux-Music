import { HttpInterceptorFn } from '@angular/common/http';
import { StorageUtil } from '../utils/storage.util';

export const tokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  // No agregar token para endpoints de autenticación
  if (req.url.includes('/auth/')) {
    return next(req);
  }

  const token = StorageUtil.getItem('token');
  if (token) {
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    console.log("Interceptor: Adding Authorization header with token");
    return next(authReq);
  }
  
  return next(req);
};
