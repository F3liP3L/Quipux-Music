import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3NTE2ODc2NjUsImV4cCI6MTc1MTc3NDA2NX0.Nv6xJECLp10Ia7nclMGLoDBGedRuSvRLPWeFpfaaPqiBaTWaOT82BSZxJdYifdD_fwfbDBElh2XyFfgn3WIJVQ";
 let authReq = req.clone({headers: req.headers.set('Authorization', `Bearer ${token}`)});
  console.log("Interceptor: Adding Authorization header with token");
  
  return next(authReq);
};
