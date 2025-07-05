import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class ListService {

  private http = inject(HttpClient);

  public getAll() {
    return this.http.get<any[]>('http://localhost:8080/lists');
  }
  public getByName(name: string) {
    return this.http.get<any>(`http://localhost:8080/lists/${name}`);
  }
}
