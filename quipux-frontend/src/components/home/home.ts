import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import { ListService } from '../../service/listService';

@Component({
  selector: 'app-home',
  imports: [CommonModule, MatCardModule],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit{

  constructor(private _listService: ListService) {}

  protected lists:any[] = [];

  protected onClick(title: string): void {
    console.log(`Clicked on ${title}`);
  }
  ngOnInit(): void {
    this._listService.getAll().subscribe({
      next: (data: any[]) => {
        this.lists = data;
      },
      error: (error) => {
        console.error('Error fetching lists:', error);
      }
    });
  }
}
