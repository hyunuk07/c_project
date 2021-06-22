import * as React from 'react';
import Persons from '../../models/member/persons';

import { Input, Button } from '../../common/components/form';

interface Props {
    persons: Persons;
    onChange: (fieldName: string, value: string) => void;
    onSave: () => void;
}

export const PersonsForm: React.FunctionComponent<Props> = (props) => { 
    return (
        <form>
            <h1>회원 관리</h1>

            <Input
                name="firstName"
                label="이름"
                value={props.persons.firstName}
                onChange={props.onChange}
            />

            <Input
                name="lastName"
                label="성"
                value={props.persons.lastName}
                onChange={props.onChange}
            />

            <Input
                name="email"
                label="이메일"
                value={props.persons.email.toString()}
                onChange={props.onChange}
            />

            <Button
                label="Save"
                className="btn btn-success"
                onClick={props.onSave}
            />
        </form>
    );
};
