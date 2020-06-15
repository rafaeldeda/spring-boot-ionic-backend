package com.rafaeldeda.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.rafaeldeda.cursomc.domain.Cliente;
import com.rafaeldeda.cursomc.domain.enums.TipoCliente;
import com.rafaeldeda.cursomc.dto.ClienteNewDTO;
import com.rafaeldeda.cursomc.repositories.ClienteRepository;
import com.rafaeldeda.cursomc.resources.exception.FieldMessage;
import com.rafaeldeda.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod())
				&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "Campo CPF não é válido"));
		}

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "Campo CNPJ não é válido"));
		}

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "e-mail já existente"));

		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}