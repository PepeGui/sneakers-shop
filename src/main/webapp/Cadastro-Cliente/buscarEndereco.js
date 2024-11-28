async function buscarEndereco() {
    const cepInput = document.getElementById('cep');
    const cep = cepInput.value.trim(); // Remove espaços em branco

    console.log(`CEP enviado: "${cep}"`); // Log para depuração

    // Validação do CEP
    if (!cep || !/^\d{8}$/.test(cep)) {
        alert("Por favor, insira um CEP válido com 8 dígitos.");
        return;
    }

    try {
        const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
        const endereco = response.data;

        if (endereco.erro) {
            alert("CEP não encontrado. Verifique o valor inserido.");
            return;
        }

        // Preenche os campos do formulário
        document.getElementById('logradouro').value = endereco.logradouro || '';
        document.getElementById('bairro').value = endereco.bairro || '';
        document.getElementById('cidade').value = endereco.localidade || '';
        document.getElementById('uf').value = endereco.uf || '';
    } catch (error) {
        console.error("Erro ao buscar o CEP:", error);
        alert("Ocorreu um erro ao buscar o CEP. Tente novamente mais tarde.");
    }
}
