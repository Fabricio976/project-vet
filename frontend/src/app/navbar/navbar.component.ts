import { Component, ElementRef, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  isDropdownOpen = false;

  constructor(private router: Router, private elementRef: ElementRef) {}

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  navigateToHome(){
    this.closeDropdown();
    this.router.navigate(['/home'])
  }

  navigateToLogin() {
    this.closeDropdown();
    this.router.navigate(['/login']);
  }
  navigateToRegister() {
    this.closeDropdown();
    this.router.navigate(['/register']);
  }


  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeDropdown();
    }
  }


}
