package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProjecao;
import com.zupacademy.magno.mercadolivre.opiniao.OpiniaoProjecao;
import com.zupacademy.magno.mercadolivre.pergunta.PerguntaProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "select " +
            "p.nome, avg(o.nota) mediaNotas, count(o.nota) qtdNotas "+
            "from opiniao o inner join produto p on (o.produto_id = p.id) "+
            "where p.id =:id group by p.id", nativeQuery = true)
    ProdutoProjecao buscaDetalhesProduto(@Param("id") Long id);

    @Query(value = "select cp.nome, cp.descricao from caracteristica_produto cp where cp.produto_id=:id", nativeQuery = true)
    List<CaracteristicaProjecao> buscaCaracteristicasPorProdutoId(@Param("id") Long id);

    @Query(value = "select link from imagem_produto where produto_id = :id", nativeQuery = true)
    List<String> buscaImagensPorProdutoId(@Param("id") Long id);

    @Query(value = "select o.titulo, o.descricao, o.nota from opiniao o where o.produto_id = :id", nativeQuery = true)
    List<OpiniaoProjecao> buscaOpinioesPorProdutoId(@Param("id") Long id);

    @Query(value = "select p.titulo, p.instante_criacao from pergunta p where p.produto_id = :id", nativeQuery = true)
    List<PerguntaProjecao> buscaPerguntasPorProdutoId(@Param("id") Long id);

}
