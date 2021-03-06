import React from 'react';
import * as toastr from 'toastr';
import Persons from '../../models/member/persons';
import BaseService from '../../service/member/base.service';
import { PersonsPage } from './page.form';
 


interface IProps { 
    history: History;
    //Map properties match
    match:{ 
        isExact: boolean
        params: {
            id:string
        },
        path: string,
        url: string,
    }
}
interface IState {
    persons: Persons
}


export default class Create extends  React.Component<IProps, IState> {
    constructor(props:IProps) {
        super(props);
         
        this.state = {
            persons: {
                firstName: '',
                lastName: '',
                email: '',
                customerId: 0
            }
        }
        this.onFieldValueChange = this.onFieldValueChange.bind(this);
    }

    private onFieldValueChange(fieldName: string, value: string) { 
        const nextState = {
            ...this.state,
            persons: {
                ...this.state.persons,
                [fieldName]: value,
            }
        };

        this.setState(nextState);
    }
    private onSave = () => { 
        BaseService.create<Persons>("/member/save", this.state.persons).then(
            (rp) => {
                if (rp.Status) {
                    toastr.success('Member saved.'); 


                    this.setState({
                        persons: {
                            firstName: '',
                            lastName: '',
                            email: '',
                            customerId: 0
                        }
                    });
                     
                } else {
                    toastr.error(rp.Messages);
                    console.log("Messages: " + rp.Messages);
                    console.log("Exception: " + rp.Exception);
                }
            }
        );

    } 
     
    render() {
        return (
            <PersonsPage
                persons={this.state.persons}
                onChange={this.onFieldValueChange}
                onSave={this.onSave}
            />
        );
    }     
     
}