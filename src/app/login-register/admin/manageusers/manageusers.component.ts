import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../../connection/user.service';
import { User } from '../../../models/user';
import { SelectItem } from 'primeng/api'; 

@Component({
  selector: 'app-manageusers',
  templateUrl: './manageusers.component.html',
  styleUrls: ['./manageusers.component.css']
})
export class ManageusersComponent implements OnInit {

  // resetPassword = new User();
  modifyThisUser = new User();
  deleteThisUser = new User();
  passwordThisUser= new User();

  userList: SelectItem[];
  userListModify: SelectItem[];

  constructor(private userService: UserService) {
    
    this.userList = [];
  }

  ngOnInit() {
    this.userService.getUsers().subscribe(
      data => {
        console.log(data);
        
        this.userList = data.map(x => {
          return { 
            label: x.email, 
            value: x.userId 
          }
        });

        this.userListModify = data.map(x => {
          return { 
            label: x.email, 
            value: x 
          }
        });
      }
    )
  }

  // createCity() {
  //   return this.cityService.createCity(this.newCity)
  //     .subscribe((res) => {
  //       console.log(res);

  //     })
  // }


  modifyUser() {
    console.log(this.modifyThisUser);
    return this.userService.modifyUser(this.modifyThisUser)
      .subscribe((res) => {
        console.log(res);

      })
  }
  
  resetPassword() {
    console.log(this.passwordThisUser);
    return this.userService.resetPassword(this.passwordThisUser)
      .subscribe((res) => {
        console.log(res);

      })
  }

  deleteUser() {
    return this.userService.deleteUser(this.deleteThisUser)
      .subscribe((res) => {
        console.log(res);

      })
  }

}
